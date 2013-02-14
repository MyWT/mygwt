package rnd.mywt.server.application;

import java.io.Serializable;
import java.math.BigInteger;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import rnd.dao.rdbms.jdbc.JDBCDataAccessObject;
import rnd.dao.rdbms.jdbc.rsmdp.ResultSetMetaDataProcessor;
import rnd.mywt.client.bean.ApplicationBean;
import rnd.mywt.client.data.ColumnMetaData;
import rnd.mywt.client.data.DataTable;
import rnd.mywt.client.data.FilterInfo;
import rnd.mywt.client.data.Row;
import rnd.mywt.client.data.impl.ColumnMetaDataImpl;
import rnd.mywt.client.data.impl.DataTableImpl;
import rnd.mywt.client.data.impl.RowImpl;
import rnd.mywt.client.data.impl.RowMetaDataImpl;
import rnd.mywt.client.expression.RowColumnExpression;
import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.mywt.client.rpc.ApplicationResponse;
import rnd.mywt.client.rpc.util.ARUtils;
import rnd.mywt.client.utils.ObjectUtils;
import rnd.mywt.server.bean.ProxyApplicationBean;
import rnd.mywt.server.data.SQLViewMetaData;
import rnd.mywt.server.data.ViewMetaData;
import rnd.mywt.server.util.AppBeanUtils;
import rnd.mywt.server.util.AppBeanUtils.BeanCopyCtx;
import rnd.mywt.server.util.AppBeanUtils.ClientBeanCopyCtx;
import rnd.mywt.server.util.AppBeanUtils.ServerBeanCopyCtx;
import rnd.op.ObjectPersistor;
import rnd.op.rdbms.JDBCObjectPersistor;
import rnd.utils.WrapperUtils;

public final class ModuleHandlerDelegate implements ModuleHandler {

	private final Map<String, ApplicationBeanHandler> beanHandlerMap;

	private final Map<String, Class> beanTypeMap;

	private final ModuleHandler moduleHandler;

	public ModuleHandlerDelegate(ModuleHandler moduleHandler) {
		this.beanHandlerMap = new HashMap<String, ApplicationBeanHandler>();
		this.beanTypeMap = new HashMap<String, Class>();
		this.moduleHandler = moduleHandler;
	}

	public void initModule() {
		moduleHandler.initModule();
	}

	public ObjectPersistor getObjectPersistor() {
		return moduleHandler.getObjectPersistor();
	}

	@Override
	public String getModuleName() {
		return moduleHandler.getModuleName();
	}

	public void registerApplicationBean(String appBeanName) {
		this.beanTypeMap.put(appBeanName, ProxyApplicationBean.class);
	}

	public void registerApplicationBean(String appBeanName, Class appBeanType) {
		this.beanTypeMap.put(appBeanName, appBeanType);
	}

	public void registerApplicationBean(String appBeanName, Class appBeanType, ApplicationBeanHandler applicationBeanHandler) {
		registerApplicationBean(appBeanName, appBeanType);
		this.beanHandlerMap.put(appBeanName, applicationBeanHandler);
	}

	@Override
	public Collection<ApplicationBeanHandler> getApplicationBeanHandlers() {
		return this.beanHandlerMap.values();
	}

	public ApplicationBeanHandler getApplicationBeanHandler(String appBeanName) {

		ApplicationBeanHandler appBeanHandler = this.beanHandlerMap.get(appBeanName);

		if (appBeanHandler == null) {
			appBeanHandler = DefaultApplicationBeanHandler.getSharedInstance();
		}

		return appBeanHandler;
	}

	public Class getApplicationBeanType(String appBeanName) {
		return this.beanTypeMap.get(appBeanName);
	}

	@Override
	public void handleRequest(ApplicationRequest req, ApplicationResponse resp) {

		switch (req.getMethod()) {

		case Save: {
			DataTable dataTable = saveOrUpateObject(req);
			if (dataTable != null) {
				if (dataTable.getRowCount() == 0) {
					resp.setThrowable(new IllegalStateException("Row not found for saved object"));
				} else {
					resp.setResult((Serializable) dataTable.getRow(0));
				}
			}
			return;
		}
		case Find: {
			Serializable savedObject = (Serializable) findObject(ARUtils.getAppBeanPKId(req), getApplicationBeanType(ARUtils.getAppBeanName(req)));
			resp.setResult(savedObject);
			return;
		}
		case Fetch: {
			DataTable dataTable = fetchDataTable(ARUtils.getAppBeanName(req), ARUtils.getViewName(req), ARUtils.getFilter(req), null);
			resp.setResult((Serializable) dataTable);
			return;
		}
		case Delete: {
			deleteObject(ARUtils.getAppBeanPKId(req), getApplicationBeanType(ARUtils.getAppBeanName(req)));
			return;
		}
		default: {
			throw new UnsupportedOperationException(req.getMethod().toString());
		}

		}
	}

	private DataTable saveOrUpateObject(ApplicationRequest req) {

		ApplicationBean applicationBean = ARUtils.getApplicationBean(req);
		Long id = applicationBean.getApplicationBeanId();

		if (id == null) {
			saveObject(applicationBean);
		} else {
			updateObject(id, applicationBean);
			DataTable dataTable = fetchDataTable(ARUtils.getAppBeanName(req), ARUtils.getViewName(req), ARUtils.getFilter(req), Collections.singleton(id));
			return dataTable;
		}
		return null;

	}

	public void deleteObject(Object id, Class objType) {
		getObjectPersistor().deleteObject(id, objType);
	}

	private DataTable fetchDataTable(String appBeanName, String viewName, FilterInfo filterInfo, Set<Long> ids) {

		ApplicationBeanHandler abHandler = getApplicationBeanHandler(appBeanName);

		ViewMetaData vmd = abHandler.getViewMetaData(viewName);
		if (vmd == null) {
			vmd = DefaultApplicationBeanHandler.getSharedInstance().getViewMetaData(viewName);
		}

		DataTable dataTable = null;

		if (vmd instanceof SQLViewMetaData) {

			SQLViewMetaData sqlvmd = (SQLViewMetaData) vmd;

			String viewQuery = sqlvmd.getViewQuery();
			// D.println("viewQuery", viewQuery);

			// Object[] params = null;
			boolean filtered = false;

			if (filterInfo != null) {
				viewQuery = new StringBuffer(viewQuery).append(" where ").append(sqlvmd.getFilterExpression(filterInfo.getFilterName())).toString();
				// params = filterInfo.getFilterParams().toArray();
				filtered = true;
				// D.println("viewQuery", viewQuery);
			}

			if (ids != null && !ids.isEmpty()) {
				StringBuffer buffer = new StringBuffer(viewQuery);
				if (filtered) {
					buffer.append(" and ");
				} else {
					buffer.append(" where ");
				}
				buffer.append(sqlvmd.getIdColumnName()).append(" in ( ").append(ObjectUtils.toString(ids, " , ")).append(" )");
				// D.println("viewQuery", viewQuery);
			}

			JDBCObjectPersistor jdbcOP = (JDBCObjectPersistor) getObjectPersistor();
			JDBCDataAccessObject jdbcDAO = jdbcOP.getDataAccessObject();

			Object[] result = (Object[]) jdbcDAO.executeQuery(viewQuery, null, JDBCDataAccessObject.ListArrayResultSetProcessor, cmdCreator, jdbcOP.getConnection(), true);
			// D.println("result", result);

			ColumnMetaData[] cmds = (ColumnMetaData[]) result[0];
			List<Object[]> columnsList = (List) result[1];

			// D.println("columnMetaDatas", columnMetaDatas);

			RowMetaDataImpl rmd = new RowMetaDataImpl(cmds);

			rmd.setModuleName(getModuleName());
			rmd.setApplicationBeanName(appBeanName);
			rmd.setViewName(viewName);

			rmd.setIdColumnIndex(sqlvmd.getIdColumnIndex());
			rmd.setDisplayColumnIndex(sqlvmd.getDisplayColumnIndex());

			dataTable = new DataTableImpl(rmd);

			for (Object[] columns : columnsList) {
				Row row = new RowImpl(rmd);
				// D.println("row", row);

				List columnList = new ArrayList(columns.length);

				for (int i = 0; i < columns.length; i++) {
					if (columns[i] instanceof BigInteger) {
						columns[i] = WrapperUtils.getLong(columns[i]);
					}
					columnList.add(columns[i]);
				}

				row.setColumns(columnList);

				dataTable.addRow(row);
			}
			// D.println("dataTable", dataTable.getRowCount());
			// D.println("dataTable", dataTable.getRows().toArray());

		} else {

			// NoSQLViewMetaData nosqlvmd = (NoSQLViewMetaData) viewMetaData;

		}

		return dataTable;

		// }
		// finally {
		// Debugger.D.pop("rnd.webapp.mwt.server.application.AbstractModuleHandler.fetch");
		// }
	}

	private static final ColumnMetaDataCreator cmdCreator = new ColumnMetaDataCreator();

	public static class ColumnMetaDataCreator implements ResultSetMetaDataProcessor {

		public ColumnMetaData[] processResultSetMetaData(ResultSetMetaData rs) throws SQLException {
			ColumnMetaDataImpl[] columnMetaDatas = new ColumnMetaDataImpl[rs.getColumnCount()];
			// D.println("columnMetaDatas", columnMetaDatas);

			for (int i = 1; i < columnMetaDatas.length + 1; i++) {

				ColumnMetaDataImpl columnMetaData = new ColumnMetaDataImpl();

				columnMetaData.setDisplayName(rs.getColumnLabel(i));
				columnMetaData.setDisplayWidth(rs.getColumnDisplaySize(i));
				columnMetaData.setName(rs.getColumnName(i));
				columnMetaData.setExpression(new RowColumnExpression(columnMetaData.getName()));
				columnMetaData.setType(rs.getColumnType(i));

				columnMetaDatas[i - 1] = columnMetaData;

				// D.println("columnMetaData[" + i + "]", columnMetaDatas[i -
				// 1]);
			}

			// D.println("columnMetaDatas", columnMetaDatas);
			return columnMetaDatas;
		}
	}

	public ApplicationBean findObject(Object id, Class<ApplicationBean> objType) {

		ApplicationBean serverBean = (ApplicationBean) getObjectPersistor().findObject(id, objType);
		serverBean.setApplicationBeanId((Long) id);

		ApplicationBean clientBean = AppBeanUtils.getNewClientBean(objType);
		AppBeanUtils.copyBean(serverBean, clientBean, this.serverCopyBeanCtx, this.clientBeanCopyCtx, new HashMap<ApplicationBean, ApplicationBean>());

		return clientBean;
	}

	private BeanCopyCtx serverCopyBeanCtx = new ServerBeanCopyCtx();

	private BeanCopyCtx clientBeanCopyCtx = new ClientBeanCopyCtx();

	@Override
	public ApplicationBean saveObject(ApplicationBean clientBean) {

		ApplicationBean serverBean = AppBeanUtils.getNewApplicationBean(AppBeanUtils.getServerBeanType(clientBean.getClass()));
		AppBeanUtils.copyBean(clientBean, serverBean, clientBeanCopyCtx, serverCopyBeanCtx, new HashMap<ApplicationBean, ApplicationBean>());

		return (ApplicationBean) getObjectPersistor().saveObject(serverBean);

	}

	public ApplicationBean updateObject(Object id, ApplicationBean clientBean) {

		ApplicationBean serverBean = (ApplicationBean) getObjectPersistor().findObject(clientBean.getApplicationBeanId(), AppBeanUtils.getServerBeanType(clientBean.getClass()));
		AppBeanUtils.copyBean(clientBean, serverBean, clientBeanCopyCtx, serverCopyBeanCtx, new HashMap<ApplicationBean, ApplicationBean>());

		return (ApplicationBean) getObjectPersistor().updateObject(serverBean.getApplicationBeanId(), serverBean);
	}

}
