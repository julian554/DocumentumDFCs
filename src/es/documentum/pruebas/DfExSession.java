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
import com.documentum.fc.client.*;
import com.documentum.fc.common.*;

public class DfExSession
{
   /**
    * Connect to the Docbase.
    */
   protected IDfSession connectToDocbase() throws IOException 
   {
      IDfSession sess = null;
      String docbase = null;
      
      try
      {
         // Returns a DFC local client object that will call into DMCL40 to 
         // connect to the Documentum servers - this is the entrance into DFC.
         IDfClient client = DfClient.getLocalClient();
         
         // Setup login credentials.
         IDfLoginInfo li = new DfLoginInfo();
         
         // Set username.
         if (DfExUtils.gUsername == null)
         {
            li.setUser(DfExUtils.getInput("Enter a Docbase username: "));
         }
         else
         {
            li.setUser(DfExUtils.gUsername);
         }
         
         // Set password.
         if (DfExUtils.gPassword == null)
         {
            li.setPassword(DfExUtils.getInput("Enter a Docbase password: "));
         }
         else
         {
            li.setPassword(DfExUtils.gPassword);
         }

         // If a docbase name was not set, get it from the user.
         if (DfExUtils.gDocbase == null)
         {
            docbase = DfExUtils.getInput("Enter a Docbase name: ");
         }
         else
         {
            docbase = DfExUtils.gDocbase;            
         }
         
         System.out.println("\nAttempting to connect to Docbase: " + docbase);      
         
         // Connect.
         sess = client.newSession(docbase, li);
         if (sess.isConnected())
         {
            System.out.println("\nConnection succeeded!");
         }
      }
      catch (DfException dfe)
      {
         System.out.println("\nError: connectToDocbase():" + dfe.toString());      
      }
      
      return  sess;
   }
   
   /**
    * Disconnect from the docbase given an instantiated IDfSession object.
    */
   protected boolean disconnectFromDocbase(IDfSession sess)
   {
      boolean retVal = false;
      
      try
      {
         // Make sure the user is connected.
         if (sess.isConnected())
         {
            // Now disconnect from the docbase.
            sess.disconnect();
            
            // For the heck of it, check the connection after the disconnect().
            if (!sess.isConnected())
            {
               System.out.println("\nSuccessfully disconnected from the docbase!\n");      
               retVal = true;
            }
            else
            {
               System.out.println("Error: disconnectFromDocbase(): the disconnect failed for some unknown reason!");      
            }
         }
         else
         {
            System.out.println("Warning: disconnectFromDocbase(): no session to disconnect!");      
         }
      }
      catch (DfException dfe)
      {
         System.out.println("\nError: disconnectFromDocbase():" + dfe.toString());      
      }
      
      return retVal;
   }
   
   /**
    * Display details about the active session.
    */
   protected void displaySessionInfo(IDfSession sess)
   {
      try
      {
         System.out.println("\nDocbase Name      : " + sess.getDocbaseName());      
         System.out.println("Server Version    : " + sess.getServerVersion());      
         System.out.println("DBMS Name         : " + sess.getDBMSName());      
         System.out.println("Docbase Owner Name: " + sess.getDocbaseOwnerName());      
         System.out.println("Session Id        : " + sess.getSessionId());      
         System.out.println("DMCL Session Id   : " + sess.getDMCLSessionId());      
         System.out.println("Docbase Id        : " + sess.getDocbaseId());      
         System.out.println("Docbase Scope     : " + sess.getDocbaseScope());      
         System.out.println("Current User      : " + sess.getLoginUserName());      
         System.out.println("Login Ticket      : " + sess.getLoginTicket());      
         System.out.println("Security Mode     : " + sess.getSecurityMode());      
      }
      catch (DfException dfe)
      {
         System.out.println("\nError: displaySessionInfo():" + dfe.toString());      
      }
   }
   
   /**
    * Program entry point.
    */
   public static void main (String argv[]) throws IOException 
   {
      System.out.println("\nBeginning DFC example application DfExSession...");
      
      // Instantiate the example app.
      DfExSession exSess = new DfExSession();
      
      // Connect to the docbase.      
      IDfSession sess = exSess.connectToDocbase();
      if (sess != null)
      {
         // Show information about the session.
         exSess.displaySessionInfo(sess);
         
         // Disconnect when the user is ready.
         DfExUtils.getInput("\nHit RETURN when ready to disconnect...");
         
         // Perform the disconnect.
         exSess.disconnectFromDocbase(sess);
      }
   }
}
