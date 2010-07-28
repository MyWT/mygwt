package rnd.mywt.server.application;

import java.io.Serializable;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import rnd.dao.DataAccessObject;
import rnd.dao.rdbms.jdbc.rsmdp.ResultSetMetaDataProcessor;
import rnd.mywt.client.bean.ApplicationBean;
import rnd.mywt.client.bean._Bean;
import rnd.mywt.client.data.ColumnMetaData;
import rnd.mywt.client.data.DataTable;
import rnd.mywt.client.data.FilterInfo;
import rnd.mywt.client.data.impl.ColumnMetaDataImpl;
import rnd.mywt.client.expression.RowColumnExpression;
import rnd.mywt.client.rpc.ApplicationRequest;
import rnd.mywt.client.rpc.ApplicationResponse;
import rnd.mywt.client.rpc.ApplicationRequest.Method;
import rnd.mywt.client.rpc.util.ARUtils;
import rnd.mywt.client.utils.ObjectUtils;
import rnd.mywt.server.data.SQLViewMetaData;
import rnd.mywt.server.data.ViewMetaData;
import rnd.mywt.server.util.ApplicationBeanUtils;
import rnd.mywt.server.util.ApplicationBeanUtils.BeanCopyHelper;
import rnd.mywt.server.util.ApplicationBeanUtils.ClientBeanCopyHelper;
import rnd.mywt.server.util.ApplicationBeanUtils.ServerBeanCopyHelper;
import rnd.op.ObjectPersistor;

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

	public void registerApplicationBean(String appBeanName, Class appBeanType) {
		this.beanTypeMap.put(appBeanName, appBeanType);
	}

	public void registerApplicationBean(String appBeanName, Class appBeanType, ApplicationBeanHandler applicationBeanHandler) {
		registerApplicationBean(appBeanName, appBeanType);
		this.beanHandlerMap.put(appBeanName, applicationBeanHandler);
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
	public void executeRequest(ApplicationRequest req, ApplicationResponse resp) {

		Method method = req.getMethod();
		// D.println("method", method);

		// Save Object
		if (method == Method.Save) {

			ApplicationBean savedObject = saveObject(ARUtils.getApplicationBean(req));
			Long savedObjectId = savedObject.getApplicationBeanId();
			DataTable dataTable = fetchDataTable(ARUtils.getAppBeanName(req), ARUtils.getViewName(req), ARUtils.getFilter(req), Collections.singleton(savedObjectId));

			if (dataTable.getRowCount() == 0) {
				resp.setThrowable(new IllegalStateException("Row not found for saved object"));
			} else {
				resp.setResult((Serializable) dataTable.getRow(0));
			}
			return;
		}
		// Find Object
		else if (method == Method.Find) {
			resp.setResult((Serializable) findObject(ARUtils.getAppBeanPKId(req), getApplicationBeanType(ARUtils.getAppBeanName(req))));
			return;
		}
		// Fetch Data Table
		else if (method == Method.Fetch) {
			// DataTable dataTable = fetchDataTable(ARUtils.getAppBeanName(req),
			// ARUtils.getViewName(req), ARUtils.getFilter(req), null);
			// resp.setResult((Serializable) dataTable);
			return;
		}
		// Delete Object
		else if (method == Method.Delete) {
			deleteObject(ARUtils.getAppBeanPKId(req), getApplicationBeanType(ARUtils.getAppBeanName(req)));
			return;
		}
		// Unknown Operation
		else {
			throw new UnsupportedOperationException(method.toString());
		}
		// }
		// finally {
		// Debugger.D.pop("rnd.webapp.mwt.server.application.AbstractModuleHandler.executeRequest");
		// }
	}

	private void deleteObject(Long appBeanPKId, Class applicationBeanType) {
		// getORMHelper().deleteObject(appBeanPKId, applicationBeanType);
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

//			Object[] params = null;
			boolean filtered = false;

			if (filterInfo != null) {
				viewQuery = new StringBuffer(viewQuery).append(" where ").append(sqlvmd.getFilterExpression(filterInfo.getFilterName())).toString();
//				params = filterInfo.getFilterParams().toArray();
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

//			Object[] result = (Object[]) JDBCDataAccessObject.get().executeQuery(viewQuery, params, JDBCDataAccessObject.ListArrayResultSetProcessor, cmdCreator, getConnection(), true);
			// D.println("result", result);
//			ColumnMetaData[] cmds = (ColumnMetaData[]) result[0];
//			List<Object[]> columnsList = (List) result[1];

			// D.println("columnMetaDatas", columnMetaDatas);

//			RowMetaDataImpl rmd = new RowMetaDataImpl(cmds);

//			rmd.setIdColumnIndex(sqlvmd.getIdColumnIndex());
//			rmd.setDisplayColumnIndex(sqlvmd.getDisplayColumnIndex());

//			dataTable = new DataTableImpl(rmd);

//			for (Object[] columns : columnsList) {
//				Row row = new RowImpl(rmd);
//				// D.println("row", row);
//
//				List columnList = new ArrayList(columns.length);
//
//				for (int i = 0; i < columns.length; i++) {
//					if (columns[i] instanceof BigInteger) {
//						columns[i] = WrapperUtils.getLong(columns[i]);
//					}
//					columnList.add(columns[i]);
//				}
//
//				row.setColumns(columnList);
//
//				dataTable.addRow(row);
//			}
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

//	private static final ColumnMetaDataCreator cmdCreator = new ColumnMetaDataCreator();
	
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

				// D.println("columnMetaData[" + i + "]", columnMetaDatas[i - 1]);
			}

			// D.println("columnMetaDatas", columnMetaDatas);
			return columnMetaDatas;
		}
	}

	// private Connection getConnection() {
	// return null;
	// // return getORMHelper().getConnection();
	// }

	public _Bean findObject(Object id, Class beanType) {
		// ApplicationBean serverBean = (ApplicationBean) getORMHelper().findObject(id, beanType);
		// serverBean.setApplicationBeanId((Long) id);
		// D.println("serverBean", serverBean);

		ApplicationBean clientBean = ApplicationBeanUtils.getNewClientBean(beanType);

		// copy(serverBean, clientBean, this.serverCopyBeanHelper, this.clientBeanCopyHelper, new
		// HashMap<ApplicationBean, ApplicationBean>());
		// D.println("clientBean", clientBean);

		return clientBean;
	}

	private BeanCopyHelper serverCopyBeanHelper = new ServerBeanCopyHelper();

	private BeanCopyHelper clientBeanCopyHelper = new ClientBeanCopyHelper();

	@Override
	public ApplicationBean findObject(Object id, Class<ApplicationBean> objType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ApplicationBean saveObject(ApplicationBean clientBean) {

		Long appBeanId = clientBean.getApplicationBeanId();
		ApplicationBean serverBean = null;

		if (appBeanId == null) {
			serverBean = ApplicationBeanUtils.getNewApplicationBean(ApplicationBeanUtils.getServerBeanType(clientBean.getClass()));
		} else {
			serverBean = getObjectPersistor().findObject(appBeanId, ApplicationBeanUtils.getServerBeanType(clientBean.getClass()));
		}

		ApplicationBeanUtils.copyBean(clientBean, serverBean, clientBeanCopyHelper, serverCopyBeanHelper, new HashMap<ApplicationBean, ApplicationBean>());

		return getObjectPersistor().saveObject(serverBean);

	}

	@Override
	public void deleteObject(Object id, Class objType) {
		// TODO Auto-generated method stub

	}

	protected ObjectPersistor<ApplicationBean> getObjectPersistor() {
		// TODO Auto-generated method stub
		return null;
	}

	protected DataAccessObject getDataAccessObject() {
		// TODO Auto-generated method stub
		return null;
	}

}
