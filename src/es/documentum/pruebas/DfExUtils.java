/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.documentum.pruebas;

/**
 *
 * @author E274399
 */

import java.io.*;
import java.util.Vector;
import com.documentum.fc.client.*;
import com.documentum.fc.common.*;

public class DfExUtils
{
   // Set these to avoid having to login for each example.
   protected static String gUsername = null;
   protected static String gPassword = null;
   protected static String gDocbase = null;
   
   /**
    * Read input from the user.
    */
   protected static String getInput(String question) throws IOException
   {
      // Input stream reader object.
      BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
      
      System.out.print(question);
      return stdIn.readLine();
   }

   /**
    * Get the object id from a given object name.
    */
   protected static String getObjectId(IDfSession sess, String objName) throws IOException 
   {
      String retVal = null;
      Vector idVec = new Vector();
      
      try
      {
         System.out.println("");
         
         // See if the object exists via a query.
         DfExSimpleQuery exQuery = new DfExSimpleQuery();
         IDfCollection col = exQuery.execQuery(sess, "select r_object_id, object_name from dm_sysobject where object_name='" + objName + "'");
         
         // Loop through the collection until there are no more rows.
         int resCount = 0;
         while (col.next())
         {
            resCount++;
            System.out.println(resCount + ".   " + col.getString("r_object_id") + "    " + col.getString("object_name"));
            
            // Save the object IDs in a vector.
            idVec.addElement(col.getString("r_object_id"));
         }
         
         // If there are results, display them and let the user choose an item.
         if (resCount != 0)
         {
            while (true)
            {
               String idxVal = getInput("\nSelect an object from the list above (1.." + resCount + "): ");
               if (!idxVal.equals("") && 
                   Integer.parseInt(idxVal) > 0 && 
                   Integer.parseInt(idxVal) <= resCount)
               {
                  retVal = (String)idVec.elementAt(Integer.parseInt(idxVal) - 1);
                  break;
               }
            }
         }
         else
         {
            System.out.println("Warning: object not found!");
         }
         
         // Close the collection.
         col.close();
      }
      catch (DfException dfe)
      {
         System.out.println("\nError: getObjectId():" + dfe.toString());      
      }
      
      return retVal;
   }
   
   // Public declarations.
   
   // Session.
   public static String SESSIONDESCRIP = 
      "Demonstrates how to establish a single \r\n" +
      "connection to the Docbase, fetch details \r\n" +
      "about the connection, then disconnect the \r\n" +
      "the session from the Docbase.";
   
   public static String SESSIONCLASSES = 
      "IDfClient\r\n" +
      "IDfLoginInfo\r\n" +
      "IDfSession";

   // Multi-session.
   public static String MULTISESSIONDESCRIP = 
      "Demonstrates establishing multiple \r\n" +
      "concurrent connections to the Docbase, \r\n" + 
      "fetching details about each connection, \r\n" + 
      "and disconnecting the sessions.";

   public static String MULTISESSIONCLASSES = 
      "IDfClient\r\n" + 
      "IDfLoginInfo\r\n" + 
      "IDfSession";

   // DocbaseMap.
   public static String DOCBASEMAPDESCRIP = 
      "Obtains all Docbase names projected by \r\n" +
      "a given docbroker and displays each one \r\n" + 
      "to the user.  When prompted with a Docbase \r\n" +
      "name, the user is given the option of \r\n" +
      "establishing a connection.";
   
   public static String DOCBASEMAPCLASSES = 
      "IDfClient\r\n" + 
      "IDfLoginInfo\r\n" + 
      "IDfSession\r\n" + 
      "IDfDocbaseMap";

   // Simple Query.
   public static String SIMPLEQUERYDESCRIP = 
      "Demonstrates how to send a query to the \r\n" +
      "server and display the result items \r\n" + 
      "contained in the returned collection.";

   public static String SIMPLEQUERYCLASSES = 
      "IDfClient\r\n" + 
      "IDfLoginInfo\r\n" + 
      "IDfSession\r\n" + 
      "IDfQuery\r\n" + 
      "IDfCollection\r\n" + 
      "IDfAttr\r\n" + 
      "IDfTypedObject";

   // Full-text Query.
   public static String FULLTEXTQUERYDESCRIP = 
      "Demonstrates how to construct a \r\n" +
      "complex query using the DFC query \r\n" +
      "builder interfaces, incorporate \r\n" +
      "full-text search capabilities, and \r\n" +
      "display the result items contained in \r\n" +
      "the collection.";
   
   public static String FULLTEXTQUERYCLASSES = 
      "IDfClient\r\n" + 
      "IDfLoginInfo\r\n" + 
      "IDfSession\r\n" + 
      "IDfQueryMgr\r\n" + 
      "IDfQueryFullText\r\n" + 
      "IDfAttrLine";

   // Create.
   public static String CREATEDESCRIP = 
      "Demonstrates how to create a new \r\n" +
      "cabinet, folder or document.";

   public static String CREATECLASSES = 
      "IDfClient\r\n" + 
      "IDfLoginInfo\r\n" + 
      "IDfSession\r\n" + 
      "IDfPersistentObject\r\n" + 
      "IDfSysObject";

   // Destroy.
   public static String DESTROYDESCRIP = 
      "Demonstrates how to destroy the current \r\n" +
      "version, or all versions of an object in \r\n" + 
      "the Docbase.";

   public static String DESTROYCLASSES = 
      "IDfClient\r\n" + 
      "IDfLoginInfo\r\n" + 
      "IDfSession\r\n" + 
      "IDfId\r\n" + 
      "IDfSysObject\r\n" + 
      "IDfPersistentObject\r\n" + 
      "IDfQuery\r\n" + 
      "IDfCollection\r\n" + 
      "IDfTypedObject";

   // Version Policy.
   public static String VERSIONPOLICYDESCRIP = 
      "Demonstrates how to view version info \r\n" +
      "for an object, as well as modify version \r\n" + 
      "info by checking in an object using the \r\n" +
      "next default version number.  Adding a \r\n" + 
      "symbolic label is also demonstrated via \r\n" +
      "the IDfSysObject.mark() method.";

   public static String VERSIONPOLICYCLASSES = 
      "IDfClient\r\n" + 
      "IDfLoginInfo\r\n" + 
      "IDfSession\r\n" + 
      "IDfId\r\n" + 
      "IDfSysObject\r\n" + 
      "IDfVersionPolicy\r\n" + 
      "IDfQuery\r\n" + 
      "IDfCollection\r\n" + 
      "IDfTypedObject";

   // Checkin/Checkout.
   public static String CHECKINCHECKOUTDESCRIP = 
      "Demonstrates how to checkin, checkout \r\n" +
      "and perform a cancel checkout on an \r\n" +
      "object.";

   public static String CHECKINCHECKOUTCLASSES = 
      "IDfClient\r\n" + 
      "IDfLoginInfo\r\n" + 
      "IDfSession\r\n" + 
      "IDfId\r\n" + 
      "IDfSysObject\r\n" + 
      "IDfFile\r\n" + 
      "IDfQuery\r\n" + 
      "IDfCollection\r\n" + 
      "IDfTypedObject";

   // ACLs.
   public static String ACLDESCRIP = 
      "Demonstrates how to change basic \r\n" +
      "permissions on an object (i.e. NONE, \r\n" +
      "BROWSE, READ, NOTE, VERSION, WRITE, \r\n" +
      "DELETE), how to change extended \r\n" +
      "permissions on an object \r\n" +
      "(CHANGE_STATE, CHANGE_PERMIT, \r\n" +
      "CHANGE_OWNER, EXECUTE_PROC, \r\n" + 
      "CHANGE_LOCATION), and create a \r\n" +
      "private ACL. ";

   public static String ACLCLASSES = 
      "IDfClient\r\n" + 
      "IDfLoginInfo\r\n" + 
      "IDfSession\r\n" + 
      "IDfId\r\n" + 
      "IDfSysObject\r\n" + 
      "IDfPersistentObject\r\n" + 
      "IDfACL\r\n" + 
      "IDfQuery\r\n" + 
      "IDfCollection\r\n" + 
      "IDfTypedObject";

   // Business Policy.
   public static String BUSINESSPOLICYDESCRIP = 
      "Demonstrates how to create a basic \r\n" +
      "business policy with 3 states, attach \r\n" + 
      "the business policy object to a document, \r\n" +
      "then promote the object through the \r\n" + 
      "lifecycle.  A second example shows how \r\n" +
      "to create a business policy with 4 \r\n" + 
      "states where one of the states is an \r\n" +
      "exception state.  The example shows \r\n" +
      "how to promote, demote, suspend and \r\n" +
      "resume the document.";

   public static String BUSINESSPOLICYCLASSES = 
      "IDfClient\r\n" + 
      "IDfLoginInfo\r\n" + 
      "IDfSession\r\n" + 
      "IDfId\r\n" + 
      "IDfSysObject\r\n" + 
      "IDfPersistentObject\r\n" + 
      "IDfQuery\r\n" + 
      "IDfCollection\r\n" + 
      "IDfTypedObject";

   // VDM.
   public static String VDMDESCRIP = 
      "Demonstrates how to view objects in a \r\n" +
      "virtual document, as well as create a \r\n" +
      "virtual document.";

   public static String VDMCLASSES = 
      "IDfClient\r\n" + 
      "IDfLoginInfo\r\n" + 
      "IDfSession\r\n" + 
      "IDfId\r\n" + 
      "IDfSysObject\r\n" + 
      "IDfPersistentObject\r\n" + 
      "IDfVirtualDocument\r\n" + 
      "IDfVirtualDocumentNode\r\n" + 
      "IDfQuery\r\n" + 
      "IDfCollection\r\n" + 
      "IDfTypedObject";

   // Workflow.
   public static String WORKFLOWDESCRIP = 
      "Demonstrates how to create and start a \r\n" +
      "workflow that is not based on a \r\n" +
      "pre-defined workflow template, and how \r\n" +
      "to create and start a workflow by \r\n" +
      "creating the workflow template \r\n" +
      "programmatically.";

   public static String WORKFLOWCLASSES = 
      "IDfClient\r\n" + 
      "IDfLoginInfo\r\n" + 
      "IDfSession\r\n" + 
      "IDfId\r\n" + 
      "IDfSysObject\r\n" + 
      "IDfPersistentObject\r\n" + 
      "IDfTime\r\n" + 
      "IDfList\r\n" + 
      "IDfWorkflow\r\n" + 
      "IDfProcess\r\n" + 
      "IDfActivity\r\n" + 
      "IDfWorkflowBuilder\r\n" + 
      "IDfQuery\r\n" + 
      "IDfCollection\r\n" + 
      "IDfTypedObject";

   // Simple Validation.
   public static String SIMPLEVALIDATIONDESCRIP = 
      "Demonstrates how to perform object and \r\n" +
      "attribute level validation, as well as \r\n" +
      "validation behavior on an erroneous \r\n" +
      "setting of an attribute.";

   public static String SIMPLEVALIDATIONCLASSES = 
      "IDfClient\r\n" + 
      "IDfLoginInfo\r\n" + 
      "IDfSession\r\n" + 
      "IDfId\r\n" + 
      "IDfPersistentObject\r\n" + 
      "IDfList\r\n" + 
      "IDfValidator\r\n" + 
      "IDfType\r\n" + 
      "IDfQuery\r\n" + 
      "IDfCollection\r\n" + 
      "IDfTypedObject\r\n";
}
