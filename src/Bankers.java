
import java.io.*;
import java.util.*;


public class Bankers {
	static int ch,i=0,j=0,k,pid,ch1;
	static int violationcheck=0,waitcheck=0; 
	static int max_avail[]=new int[6] ;         // list of available resources at each state
    static int sseq[]=new int[10];
    static int ss=0,check1=0,check2=0,n;
    static int work[]=new int[6];
    static int request[];
    static int claim_mat[][];
    static int allo[][];
    static int need[][];
    static int finish[];    
    static int nor=0,nori;
	public static void main(String arg[]) throws Exception
	{
    
 	InputStreamReader isr=new InputStreamReader(System.in);
	BufferedReader obj=new BufferedReader(isr);		
	
 
          do
          {
	 
		System.out.println("\n\n\t ********* MENU **************");
	 	System.out.println("\n\n\t 1. Read Initial State");
	 	System.out.println("\n\n\t 2. Read New Request");
	 	System.out.println("\n\n\t 3. Check State Safe or Unsafe");
	 	System.out.println("\n\n\t 4. Display All vector & Matrices");
	 	System.out.println("\n\n\t 5. Exit");
	 	System.out.println("\n\n\t Enter ur choice : ");
	     ch=Integer.parseInt(obj.readLine());

		switch(ch)
      {

	case 1:

		System.out.println("\n\n\t Enter number of processes : ");
		n=Integer.parseInt(obj.readLine());
		finish=new int[n];
		System.out.println("\n\n\t Enter the Number of Resources :");
	    nor=Integer.parseInt(obj.readLine());
	    request=new int[nor];
	    claim_mat=new int[n][nor];
	    allo=new int[n][nor];
	    need=new int[n][nor];
		System.out.println("\n\n\t Enter the Maximum Available Resouces : ");
		for(j=0;j<n;j++)
		{
		 for(k=0;k<nor;k++)
		 {
		  if(j==0)
		  {
		    System.out.println("\n\n\t For Resource type: "+k);
		    max_avail[k]=Integer.parseInt(obj.readLine());
		   }
		  claim_mat[j][k]=0;    //initialize all to 0
		  allo[j][k]=0;
		  need[j][k]=0;
		  finish[j]=0;
		  request[k]=0;
		 }
		}
	
		for(i=0;i<n;i++)
		{
		 System.out.println("\n\n\t Enter Max_claimed and Allocated resources for P"+i);
		  for(j=0;j<nor;j++)
		  {
		   System.out.println("\n\n\t Enter the Max Claimed of resource="+j);
		   claim_mat[i][j]=Integer.parseInt(obj.readLine());
		   System.out.println("\n\n\t Allocation of resource="+j);
		   allo[i][j]=Integer.parseInt(obj.readLine());
		   if(allo[i][j]>claim_mat[i][j])
		   {
			   System.out.println("\n\n\t Error: Allocation should be less < or == Max_claim");
		     j--;
		   }
		   else
		    need[i][j]=claim_mat[i][j]-allo[i][j];
		    max_avail[j]=max_avail[j]-allo[i][j];
		 }
		}
	       break;

	case 2:
		violationcheck=0;
		waitcheck=0;
		System.out.println("Enter Requesting process_id:");
		pid=Integer.parseInt(obj.readLine());
		for(j=0;j<nor;j++)
		{
		  System.out.println("Number of Request for resource="+j);
		  request[j]=Integer.parseInt(obj.readLine());
		  if(request[j]>need[pid][j]) //total request > claim or 
		   violationcheck=1;          // error
		  if(request[j]>max_avail[j])
		   waitcheck=1;      //suspend
		 }

		 if (violationcheck==1)
			 System.out.println("The Process Exceeds it's Max Need: Terminated");
		 else if(waitcheck==1)
			 System.out.println(" Lack of Resourcess : Process State is  Wait");
		 else                               // define a new state
		 {
		   for(j=0;j<nor;j++)              //carry out allocation
		   {
		     max_avail[j]=max_avail[j]-request[j];
		     allo[pid][j]=allo[pid][j]+request[j];
		     need[pid][j]=need[pid][j]-request[j];
		   }
		   ch1=safeseq();         //check new request generates safe state or not
		   if(ch1==0)
		   {
		  System.out.println("Grantingg leads to Unsafe state : ");
		    System.out.println("Request Denied & Restore original state ");
		    for(j=0;j<nor;j++)
		    {
		    	 max_avail[j]=max_avail[j]+request[j];
			     allo[pid][j]=allo[pid][j]-request[j];
			     need[pid][j]=need[pid][j]+request[j];
			}
		   }
		   else if(ch1==1)
		     System.out.println(" Request Committed ");
		 }
		 break;
	case 3:
		if(safeseq()==1)
		  System.out.println("The System is in safe state ");
		else
		  System.out.println("The System is Not in safe state ");
		break;

	case 4:
		System.out.println("Number of processes :"+n);
		System.out.println("Number of Resources :"+nor);
		System.out.println("Pid \t\tClaim_Mat \t\tAllocated \t\tNeed ");
		for(i=0;i<n;i++)
		{
			System.out.print(i+"\t\t");
		 for(j=0;j<nor;j++)
			 System.out.print(claim_mat[i][j]+" ");
		 System.out.print("\t\t\t");
		 
		 for(j=0;j<nor;j++)
			 System.out.print(allo[i][j]+" ");
		 System.out.print("\t\t\t");
		 for(j=0;j<nor;j++)
			 System.out.print(need[i][j]+" ");
		 System.out.println();
		}

		System.out.println("Available Resources: ");
		for(i=0;i<nor;i++)
			System.out.print(max_avail[i]+"\t");
		break;

	case 5:
		break;

      }

    }while(ch!=5);
	
	}

static int safeseq()             // checks new request leads to safe /unsafe state
{

  int tj,i,tk,j,k,temp;
  ss=0;

  for(j=0;j<nor;j++)
    work[j]=max_avail[j]; //currently available resources
  for(j=0;j<n;j++)
    finish[j]=0;
  for(tk=0;tk<nor;tk++)
  {
    for(j=0;j<n;j++)
    {
     if(finish[j]==0)
     {
      check1=0; temp=0;
      for(k=0;k<nor;k++)
	if(need[j][k]<=work[k])
	{
	  check1++;
	  if(need[j][k]==work[k])
	   temp++;
	}
      if(temp==nor)
       continue;
      else if(check1==nor)
       {
	 for(k=0;k<nor;k++)
	 {
	  work[k]=work[k]+allo[j][k]; //process satisfies requirement and completed, free resources allocated to it
	  finish[j]=1; //set status finished
	 }
	 sseq[ss]=j; //safe sequence of process execution
	 ss++;
       }
     }
    }
  }
 check2=0;
 for(i=0;i<n;i++)
  if(finish[i]==1)
    check2++;
    System.out.println("\n\n\t");
  if(check2>=n)
  {
   System.out.println("\n\n\t The system is in safe state");
   for(tj=0;tj<n;tj++)
    System.out.print("P"+sseq[tj]+"->");
    return 1;
  }
  else
   System.out.println("\n\n\t The system is Not in safe state");

  return 0;
}


}
