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

public class DfExSimpleQuery
{
   /**
    * Execute the query.
    */
   protected IDfCollection execQuery(IDfSession sess, String queryString)
   {
      IDfCollection col = null;
      
      try
      {
         // Create the query object then set the query string.
         IDfQuery q = new DfQuery();
         q.setDQL(queryString);
         
         // Synchronously execute the query.
         col = q.execute(sess, DfQuery.DF_READ_QUERY);
      }
      catch (DfException dfe)
      {
         System.out.println("\nError: execQuery():" + dfe.toString());      
      }
      
      return col;
   }
   
   /**
    * Display the results of the query.
    */
   protected void displayResults(IDfCollection col) throws IOException 
   {
      try
      {
         // Loop through the collection until there are no more rows.
         int resItems = 1;
         while (col.next())
         {
            System.out.println("\nResult row: " + resItems++);      
            
            // Spin through each attribute.
            for (int i = 0; i < col.getAttrCount(); i++)
            {
               IDfAttr attr = col.getAttr(i);
               System.out.print("\t" + attr.getName() + ": ");
               
               // Display the value of the attribute - branch according to datatype.
               if (attr.getDataType() == attr.DM_BOOLEAN)
               {
                  System.out.println(col.getBoolean(attr.getName()));
               }
               else if (attr.getDataType() == attr.DM_DOUBLE)
               {
                  System.out.println(col.getDouble(attr.getName()));
               }
               else if (attr.getDataType() == attr.DM_ID)
               {
                  System.out.println(col.getId(attr.getName()).toString());
               }
               else if (attr.getDataType() == attr.DM_INTEGER)
               {
                  System.out.println(col.getInt(attr.getName()));
               }
               else if (attr.getDataType() == attr.DM_STRING)
               {
                  System.out.println(col.getString(attr.getName()));
               }
               else if (attr.getDataType() == attr.DM_TIME)
               {
                  System.out.println(col.getTime(attr.getName()).toString());
               }
               else
               {
                  System.out.println("Error: displayResults(): unrecognized datatype!");      
               }
            }
            
            String response = DfExUtils.getInput("\nHit RETURN for next row, 'Q' to quit: ");
            if (response.toUpperCase().equals("Q"))
            {
               break;
            }
         }
         
         // Close the collection.
         col.close();
      }
      catch (DfException dfe)
      {
         System.out.println("\nError: displayResults():" + dfe.toString());      
      }
   }
   
   /**
    * Program entry point.
    */
   public static void main (String argv[]) throws IOException 
   {
      System.out.println("\nBeginning DFC example application DfExSimpleQuery...");
      
      // Instantiate the session object.
      DfExSession exSess = new DfExSession();
      
      // Connect to the docbase.      
      IDfSession sess = exSess.connectToDocbase();
      if (sess != null)
      {
         // Instantiate the example app.
         DfExSimpleQuery exQuery = new DfExSimpleQuery();

         while (true)
         {
            // Obtain the query string from the user.
            String queryString = DfExUtils.getInput("\nEnter a DQL query, 'Q' to quit: ");
            if (queryString.toUpperCase().equals("Q"))
            {
               break;
            }
            
            // Show information about the session.
            IDfCollection col = exQuery.execQuery(sess, queryString);
            if (col != null)
            {
               // Display the query results contained in the collection object.
               exQuery.displayResults(col);
            }
            else
            {
               System.out.println("\nWarning: query returned an empty collection!\n");      
            }
         }
         
         // Perform the disconnect.
         exSess.disconnectFromDocbase(sess);
      }
   }
}

