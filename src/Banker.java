import java.io.*;
import java.util.*;

public class Banker {
	static int max_available[] = new int[6];
	static int max_claim[][];
	static int allocation[][];
	static int need[][];
	static int sseq[]=new int[10];
	static int work[]=new int[6];
	static int finish[];
	static int request[];
	static int violationcheck=0,waitcheck=0; 
	static int n,m,check1=0,check2=0,ss=0,ch,pid,ch1;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		do
		{
			System.out.println("\n\n\t ############ MENU ############");
		 	System.out.println("\n\n\t 1. Read Initial State");
		 	System.out.println("\n\n\t 2. Read New Request");
		 	System.out.println("\n\n\t 3. Check Safe or Unsafe State");
		 	System.out.println("\n\n\t 4. Display All vector & Matrices");
		 	System.out.println("\n\n\t 5. Exit");
		 	System.out.println("\n\n\t Enter ur choice : ");
		    ch=sc.nextInt();
		    
		    switch(ch)
		    {
		        case 1:
		        {
		        	System.out.println("\nEnter the number of process: ");
		   		    n = sc.nextInt();
		   		    finish=new int[n];
		   		    System.out.println("\nEnter the number of resource types: ");
		   		    m = sc.nextInt();
		   		    request=new int[m];
		   		    max_claim = new int[n][m];
		 		    allocation = new int[n][m];
		 		    need = new int[n][m];
		 			System.out.println("\n\t Enter the Maximum Available Resouces : ");
		 			for(int j=0;j<n;j++)
		 			{
		 				for(int k=0;k<m;k++)
		 				{
		 					if(j==0)
		 					{
		 					    System.out.println("\n\n\t For Resource type: "+k);
		 					   max_available[k] = sc.nextInt();
		 					}
		 					max_claim[j][k]=0;
		 					allocation[j][k]=0;
		 					need[j][k]=0;
		 					finish[j]=0;
		 					request[k]=0;
		 				}
		 			}
		 			
		 			System.out.println("Enter the values in the maximum claim matrix: \n");
		 			for(int i=0;i<n;i++)
		 			{
		 				for(int j=0;j<m;j++)
		 				{
		 					max_claim[i][j] = sc.nextInt();
		 					
		 				}	
		 			}
		 			System.out.println("\n");
		 			
		 			System.out.println("Enter the values in the allocation matrix: \n");
		 			for(int i=0;i<n;i++)
		 			{
		 				for(int j=0;j<m;j++)
		 				{
		 					allocation[i][j] = sc.nextInt();
		 					
		 				}		
		 			}
		 			System.out.println("\n");
		 			
		 			System.out.println("Calculation of need matrix: \n");
		 			for(int i=0;i<n;i++)
		 			{
		 				for(int j=0;j<m;j++)
		 				{
		 					if(allocation[i][j]>max_claim[i][j])
		 					{
		 					   System.out.println("\n\n\t Error: Allocation should be less < or == Max_claim");
		 					   j--;
		 					}
		 					need[i][j] = max_claim[i][j] - allocation[i][j];
		 					max_available[j] = max_available[j] - allocation[i][j];
		 				}
		 			
		 			}
		 			
		 			
		 			
		   		    break;
		        }
		        
		        case 2:
		        {
		        	violationcheck=0;
		    		waitcheck=0;
		    		System.out.println("Enter Requesting process_id:");
		    		pid=sc.nextInt();
		    		for(int j=0;j<m;j++)
		    		{
		    		  System.out.println("Number of Request for resource="+j);
		    		  request[j]=sc.nextInt();
		    		  if(request[j]>need[pid][j])
		    		  {//total request > claim or 
		    		      violationcheck=1;   
		    		  }// error
		    		  if(request[j]>max_available[j])
		    		  {
		    			  waitcheck=1;      //suspend
		    		  }
		    		  
		    		 }

		    		 if (violationcheck==1)
		    		 {
		    			 System.out.println("The Process Exceeds it's Max Need: Terminated");
		    		 }
		    		 else if(waitcheck==1)
		    		 {
		    			 System.out.println(" Lack of Resourcess : Process State is  Wait");
		    		 }
		    		 else                               // define a new state
		    		 {
		    		   for(int j=0;j<m;j++)              //carry out allocation
		    		   {
		    			 max_available[j]=max_available[j]-request[j];
		    		     allocation[pid][j]=allocation[pid][j]+request[j];
		    		     need[pid][j]=need[pid][j]-request[j];
		    		   }
		    		   ch1=safesequence();         //check new request generates safe state or not
		    		   if(ch1==0)
		    		   {
		    		    System.out.println("Grantingg leads to Unsafe state : ");
		    		    System.out.println("Request Denied & Restore original state ");
		    		    for(int j=0;j<m;j++)
		    		    {
		    		    	 max_available[j]=max_available[j]+request[j];
		    			     allocation[pid][j]=allocation[pid][j]-request[j];
		    			     need[pid][j]=need[pid][j]+request[j];
		    			}
		    		   }
		    		   else if(ch1==1)
		    		     System.out.println(" Request Committed ");
		    		 }
		        	break;
		        }
		        
		        case 3:
		        {
		        	if(safesequence()==1)
		        	{
		        		System.out.println("The System is in safe state");
		        	}
		        	else
		        	{
		        		System.out.println("The System is NOT in safe state");
		        	}
		        	break;
		        }
		        
		        case 4:
		        {
		        	System.out.println("Number of processes :"+n);
		    		System.out.println("Number of Resources :"+m);
		    		System.out.println("Pid \t\tClaim_Mat \t\tAllocated \t\tNeed ");
		    		for(int i=0;i<n;i++)
		    		{
		    			System.out.print(i+"\t\t");
		    		 for(int j=0;j<m;j++)
		    			 System.out.print(max_claim[i][j]+" ");
		    		 System.out.print("\t\t\t");
		    		 
		    		 for(int j=0;j<m;j++)
		    			 System.out.print(allocation[i][j]+" ");
		    		 System.out.print("\t\t\t");
		    		 for(int j=0;j<m;j++)
		    			 System.out.print(need[i][j]+" ");
		    		 System.out.println();
		    		}

		    		System.out.println("Available Resources: ");
		    		for(int i=0;i<m;i++)
		    			System.out.print(max_available[i]+"\t");
		        	break;
		        }
		        
		        case 5:
		        {
		        	break;
		        }
		    	
		    }

		}
		while(ch!=5);
		
	}
	
	static int safesequence()
	{
		int temp,tk,tj;
		//ss=0;
		for(int j=0;j<m;j++)
		{
			work[j] = max_available[j];
		}
		
		for(int j=0;j<n;j++)
		{
			finish[j] = 0;
		}
		
		for(tk=0;tk<m;tk++)
		{
			for (int j=0;j<n;j++)
			{
				if(finish[j]==0)
				{
					check1 = 0;
					temp = 0;
					for(int t=0;t<m;t++)
					{
						if(need[j][t]<=work[t])
						{
							check1++;
							if(need[j][t]==work[t])
								temp++;
						}
					
						if(temp==m)
						{
							continue;
						}
						else if(check1==m)
						{
							for(int t1=0;t1<m;t1++)
							{
								work[t1] = work[t1] + allocation[j][t1];
								finish[j]=1;
							}
							sseq[ss]=j;
							ss++;
						}
					}
				}
			}
		}
		
		check2=0;
		for(int i=0;i<n;i++)
		{
			if(finish[i]==1)
			{
				check2++;
				//System.out.println("\n\n\t");
			}
		}
		
			if(check2>=n)
			{
				System.out.println("\n\n\t The system is in safe state");
				for(tj=0;tj<n;tj++)
				{
					System.out.print("P"+sseq[tj]+"->");
					return 1;
				}
			}
			else
			{
				System.out.println("\n\n\t The system is not in safe state");
			}
			
		
		return 0;
	}

}


 
