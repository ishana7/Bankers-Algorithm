import java.io.*;
import java.util.*;
public class BankAlgo {
	
	static int ch1,n,m;
	static int max_available[] = new int[6];
	static int max_claim[][];
	static int allocation[][];
	static int need[][];
	static int finish[];
	static int request[];
	static int sseq[]=new int[10];
	static int work[]=new int[6];
	static int violationcheck=0,waitcheck=0; 
	static int pid,ch,ss=0,check1,check2;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc= new Scanner(System.in);
		do 
		{
			System.out.println("\n\n\t ############ MENU ############");
		 	System.out.println("\n\n\t 1. Read Initial State");
		 	System.out.println("\n\n\t 2. Read New Request");
		 	System.out.println("\n\n\t 3. Check Safe or Unsafe State");
		 	System.out.println("\n\n\t 4. Display All vector & Matrices");
		 	System.out.println("\n\n\t 5. Exit");
		 	System.out.println("\n\n\t Enter ur choice : ");
		    ch1=sc.nextInt();
		    
		    switch(ch1)
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
				 	System.out.println("Enter requesting resource id:");
				 	pid=sc.nextInt();
			    	for(int i=0;i<m;i++)
			    	{
			    		System.out.println("New resource request for "+i+":");
			    		request[i]=sc.nextInt();
			    		
			    		if(request[i]>need[pid][i])
			    		{
			    			violationcheck=1;
			    			
			    		}
			    		if(request[i]>max_available[i])
			    		{
			    			waitcheck=1;
			    		}
			    		
			    	}
			    	
			    	if(violationcheck==1)
			    	{
			    		System.out.println("Violation occred as request excceds need");
			    	}
			    	
			    	else if(waitcheck==1)
			    	{
			    		System.out.println("Process enters wait stage ");
			    	}
			    	else
			    	{
			    		for(int i=0;i<m;i++)
			    		{
			    			max_available[i] = max_available[i]-request[i];
			    			allocation[pid][i] = allocation[pid][i]+request[i];
			    			need[pid][i] = need[pid][i] - request[i];
			    		}
			    		
			    		ch=safesequence();
			    		
			    		if(ch==0)
			    		{
				    		System.out.println("Unsafe sequence");
				    		System.out.println("Restore previous values:");
				    		for(int i=0;i<m;i++)
				    		{
				    			max_available[i] = max_available[i]+request[i];
				    			allocation[pid][i] = allocation[pid][i]-request[i];
				    			need[pid][i] = need[pid][i] + request[i];
				    		}
			    		}
			    		else if(ch==1)
			    		{
			    			System.out.println("safe sequence");
			    		}
			    		
			    	}
			    	
			    	break;
			    }
			    
			    case 3:
			    {
			    	if(safesequence()==1)
			    	{
			    		System.out.println("safe sequence");
			    	}
			    	else
			    	{
			    		System.out.println("Unsafe sequence");
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
		while(ch1!=5);

	}
	
	static int safesequence()
	{
		int temp;
		for(int j=0;j<n;j++)
		{
			work[j]=max_available[j];
		}
		for(int j=0;j<m;j++)
		{
			finish[j]=0;
		}
		
		for(int k=0;k<m;k++)
		{
			for(int j=0;j<n;j++)
			{
				if(finish[j]==0)
				{
					check1=1;
					temp=0;
					for(int tk=0;tk<m;tk++)
					{
						if(need[j][tk]<=work[tk])
						{
							check1++;
							if(need[j][tk]==work[tk])
								temp++;
						}
						
						if(temp==m)
						{
							continue;
						}
						else if(check1==m)
						{
							for(int i=0;i<m;i++)
							{
								work[i] = work[i]+allocation[j][i];
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
			if(finish[i]==1)
				check2++;
		    if(check2>=n)
			{
				System.out.println("safe sequence");
			    for(int k=0;k<n;k++)
			    	System.out.println("P"+sseq[k]+"-->");
			        return 1;
			}
			else
			{
				System.out.println("Unsafe sequence");
			}
		return 0;
	}

}
