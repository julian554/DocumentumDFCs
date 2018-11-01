package es.documentum.pruebas;


import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.documentum.fc.client.DfClient;
import com.documentum.fc.client.DfQuery;
import com.documentum.fc.client.IDfClient;
import com.documentum.fc.client.IDfModule;
import com.documentum.fc.client.IDfPersistentObject;
import com.documentum.fc.client.IDfQuery;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfId;
import com.documentum.fc.common.DfLoginInfo;
import com.documentum.fc.common.IDfId;
import com.documentum.fc.common.IDfLoginInfo;
import com.documentum.fc.client.IDfCollection;
import com.documentum.fc.methodserver.IDfMethod;

public class RunningMethodThroughDFC implements IDfMethod, IDfModule {

	static String methodName = "dm_DataDictionaryPublisher";
	static String jobName = "dm_DataDictionaryPublisher";
	static String methodArguments = "-docbase_name dev_doc.dev_doc -user_name documentum -method_trace_level 5";

	protected String m_docbase = null;
	protected String m_userName = null;
	protected String m_password = null;
	protected String m_domain = null;
	protected String m_jobid = null;
	protected String m_mtl = "0";
	// Default parameters passed by invocation of job
	private static final String USER_KEY = "user_name";
	private static final String DOCBASE_KEY = "docbase_name";
	private static final String PASSWORD_KEY = "password";
	private static final String DOMAIN_KEY = "domain";
	private static final String JOBID = "job_id";
	private static final String MTL = "method_trace_level";
	private static final String NEWLINE = "\r\n";

	protected String m_strJobName = null;
	protected String m_strServerLog = "";
	protected String m_strFilename = "";
	protected RandomAccessFile m_File = null;

	IDfSessionManager sessionManager = null;
	IDfSession session = null;

	// Main method

	public void execute(Map parameters, OutputStream output) throws Exception {
		
/*		
		IDfSysObject oIDfJob = null;
		boolean ejecutado = true;
		try {
			output.write(("Arrancamos metodo EjecutarMetodo" + NEWLINE)
					.getBytes());
			System.out.println("Arrancamos metodo EjecutarMetodo");
			initParams(parameters);
			sessionManager = login();
			session = sessionManager.getSession(m_docbase);

			if (!"local".equals(m_jobid)) {
				oIDfJob = (IDfSysObject) session.getObject(new DfId(m_jobid));
				m_strJobName = oIDfJob.getObjectName();
				if (m_strJobName.startsWith("dm_"))
					m_strJobName = m_strJobName.substring(3);
				// oResultDoc = makeResultDoc(session, pw );
			}

			IDfQuery q = new DfQuery();
			String methObjectId = null;
			// IDfSession session = getSession();

			q.setDQL("select r_object_id from dm_method where object_name='"
					+ methodName + "'");
			IDfCollection coll = q.execute(session, DfQuery.DF_READ_QUERY);
			while (coll.next()) {
				methObjectId = coll.getId("r_object_id").toString();
			}
			coll.close();
			output.write(("Object ID of the method: " + methObjectId + NEWLINE)
					.getBytes());
			m_File.write(("Object ID of the method: " + methObjectId + NEWLINE)
					.getBytes());
			System.out.println("Object ID of the method: " + methObjectId);
			IDfQuery qry = new DfQuery();
			String jobObjectId = null;
			qry.setDQL("select r_object_id from dm_job where object_name='"
					+ jobName + "'");
			IDfCollection colln = qry.execute(session, DfQuery.DF_READ_QUERY);
			while (colln.next()) {
				jobObjectId = colln.getId("r_object_id").toString();
			}
			colln.close();

			methodArguments = "-docbase_name " + session.getDocbaseName()
					+ " -user_name " + session.getLoginInfo().getUser()
					+ " -method_trace_level 5 -window_interval 1440";

			String methodCmd = "DO_METHOD,METHOD,S," + methodName
					+ ",TIME_OUT,I,200,SAVE_RESULTS,B,T,ARGUMENTS,S,"
					+ methodArguments + " -job_id " + jobObjectId;
			// Obtaining handle to the method
			IDfId sysObjID = new DfId(methObjectId);
			IDfSysObject sysObject = (IDfSysObject) session.getObject(sysObjID);
			// Executing the method
			@SuppressWarnings("deprecation")
			String methResult = sysObject.apiGet("apply", methodCmd);
			output.write(("Result of executing the method: " + methResult + NEWLINE)
					.getBytes());
			m_File.write(("Result of executing the method: " + methResult + NEWLINE)
					.getBytes());
			System.out.println("Result of executing the method: " + methResult);
			sysObject.save();
			output.write(("method executed successfully!!" + NEWLINE)
					.getBytes());
		} catch (DfException e) {
			output.write(("main::Exception is " + e.getMessage() + NEWLINE)
					.getBytes());
			m_File.write(("main::Exception is " + e.getMessage() + NEWLINE)
					.getBytes());
			System.out.println("main::Exception is " + e.getMessage());
		} finally {
			Date dtEnd = new Date();
			String strStatus = m_strJobName;
			if (ejecutado)
				strStatus += " Completado sin errores " + dtEnd.toString();
			else
				strStatus += " Error " + dtEnd.toString()
						+ " Consulte los detalles del Log";

			if (oIDfJob != null) {

				oIDfJob.setString("a_current_status", strStatus);
				oIDfJob.save();
			}
			sessionManager.release(session);
			output.write(("Fin del metodo" + NEWLINE).getBytes());
			System.out.println("Fin del metodo");
			m_File.close();
		}
*/
	
	}

	protected void initParams(Map params) throws Exception {
		Set keys = params.keySet();
		Iterator iter = keys.iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();

			if ((key == null) || (key.length() == 0)) {
				continue;
			}
			String[] value = (String[]) params.get(key);

			if (key.equalsIgnoreCase(USER_KEY))
				m_userName = (value.length > 0) ? value[0] : "";
			else if (key.equalsIgnoreCase(DOCBASE_KEY))
				m_docbase = (value.length > 0) ? value[0] : "";
			else if (key.equalsIgnoreCase(PASSWORD_KEY))
				m_password = (value.length > 0) ? value[0] : "";
			else if (key.equalsIgnoreCase(DOMAIN_KEY))
				m_domain = (value.length > 0) ? value[0] : "";
			else if (key.equalsIgnoreCase(JOBID))
				m_jobid = (value.length > 0) ? value[0] : "";
			else if (key.equalsIgnoreCase(MTL))
				m_mtl = (value.length > 0) ? value[0] : "";
		}
	}

	protected IDfSessionManager login() throws DfException {
		if (m_docbase == null || m_userName == null)
			return null;
		// now login
		IDfClient dfClient = DfClient.getLocalClient();

		if (dfClient != null) {
			IDfLoginInfo li = new DfLoginInfo();
			li.setUser(m_userName);
			li.setPassword(m_password);
			li.setDomain(m_domain);

			IDfSessionManager sessionMgr = dfClient.newSessionManager();
			sessionMgr.setIdentity(m_docbase, li);
			return sessionMgr;
		}
		return null;
	}

	@Override
	public int execute(Map parameters, PrintWriter writer) throws Exception {
		IDfSysObject oIDfJob = null;
		boolean ejecutado = true;
		try {
			initParams(parameters);
			sessionManager = login();
			session = sessionManager.getSession(m_docbase);

			writer.append("Iniciando ejecucion del job " + NEWLINE);
			writer.append("Repositorio: " + session.getDocbaseName() + NEWLINE);

			if (!"local".equals(m_jobid)) {
				oIDfJob = (IDfSysObject) session.getObject(new DfId(m_jobid));
				m_strJobName = oIDfJob.getObjectName();
				if (m_strJobName.startsWith("dm_"))
					m_strJobName = m_strJobName.substring(3);
				// oResultDoc = makeResultDoc(session, pw );
			}

			IDfQuery q = new DfQuery();
			String methObjectId = null;
			// IDfSession session = getSession();

			q.setDQL("select r_object_id from dm_method where object_name='"
					+ methodName + "'");
			IDfCollection coll = q.execute(session, DfQuery.DF_READ_QUERY);
			while (coll.next()) {
				methObjectId = coll.getId("r_object_id").toString();
			}
			coll.close();
			writer.append("Object ID of the method: " + methObjectId + NEWLINE);
			IDfQuery qry = new DfQuery();
			String jobObjectId = null;
			qry.setDQL("select r_object_id from dm_job where object_name='"
					+ jobName + "'");
			IDfCollection colln = qry.execute(session, DfQuery.DF_READ_QUERY);
			while (colln.next()) {
				jobObjectId = colln.getId("r_object_id").toString();
			}
			colln.close();

			methodArguments = "-docbase_name " + session.getDocbaseName()
					+ " -user_name " + session.getLoginInfo().getUser()
					+ " -method_trace_level 5 -window_interval 1440";

			String methodCmd = "DO_METHOD,METHOD,S," + methodName
					+ ",TIME_OUT,I,200,SAVE_RESULTS,B,T,ARGUMENTS,S,"
					+ methodArguments + " -job_id " + jobObjectId;

			writer.append("Metodo a ejecutar: " + methodCmd + NEWLINE);
			// Obtaining handle to the method
			IDfId sysObjID = new DfId(methObjectId);
			IDfSysObject sysObject = (IDfSysObject) session.getObject(sysObjID);
			// Executing the method
			@SuppressWarnings("deprecation")
			String methResult = sysObject.apiGet("apply", methodCmd);
			session.apiExec("next", methResult);
			String APIResult = session.apiGet("get", methResult + ",result");

			writer.append("Result of executing the method: " + APIResult
					+ NEWLINE);
			sysObject.save();
			writer.append("method executed successfully!!" + NEWLINE);
		} catch (Exception e) {
			writer.append("main::Exception is " + e.getMessage() + NEWLINE);
		} finally {
			Date dtEnd = new Date();
			String strStatus = m_strJobName;
			if (ejecutado)
				strStatus += " Completado sin errores " + dtEnd.toString();
			else
				strStatus += " Error " + dtEnd.toString()
						+ " Consulte los detalles del Log";

			if (oIDfJob != null) {

				oIDfJob.setString("a_current_status", strStatus);
				oIDfJob.save();
			}
			sessionManager.release(session);
			writer.append("\nFin del metodo.\n");
		}

		writer.flush();
		writer.close();

		return 0;
	}

}


