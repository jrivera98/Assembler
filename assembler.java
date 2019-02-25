/**
 * @author Josette Rivera
 * @date 3/12/18
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public class assembler 
{
	public static void main(String[] args) throws IOException
	{
		// input file
		BufferedReader file_input = new BufferedReader(new FileReader("test_file.txt"));
		
		String buff;
		
		// output file
		File file = new File ("file_output.txt");
		FileWriter fw = new FileWriter(file);
		PrintWriter pw = new PrintWriter(fw);
		
		int addressOfline=96;
		
		do
		{
			addressOfline = addressOfline+4;
			//System.out.println(addressOfline);
			
			buff=file_input.readLine();
			
			if (buff == null) break;
			
			String I_type[] = {"lb","lh","lw","lbu","lhu","sb","sh","sw","jr","jalr","beqz",
							"bnez","addi","addiu","subi","subui","divu","multu","lhi",
							"seqi","snei","slti","sgti","slei","sgei","sequi","sneui",
							"sltui","sgtui","sleui","sgeui","slli","srli","srai"};
							//(load,store, jump,branch,add,sub,div,mult,lhi,test,shift)
		
			String R_type[] = {"add","addu","sub","subu","div","mult","seq","sne","slt","sgt",
							"sle","sge","sll","srl","sra"};//(add,sub,div,mult,test,shift)
							
			String J_type[] = {"j","jal"};//(jump)

			int numComma = buff.replaceAll("[^,]","").length();//count comma 			
//========================================================================================================================				
			if (buff.contains(":") == true)// start: addi r1,r2,r3
			{
				String ln[]=buff.split(":");
				String lable = ln[0];
				String line = ln[1];
				System.out.println(lable);
				System.out.println(line);
				
				String split[] = line.split(" ");
				String inst = split[1].toLowerCase();
				System.out.println("\n"+inst);
	//========================================================================================================================	
							
				if(Arrays.asList(I_type).contains(inst))
				{
					// --------------------------------------------------------------------------------------------------------------------
					if (numComma == 0) // jr r1 (jump)
					{
						String source =split[2].replaceAll("[^0-9]", "");
						System.out.println(source);
						
						String op_bits = BinaryOp_bits(inst);
						String rs1_bits = Integer.toBinaryString(Integer.parseInt(source));
						System.out.println(op_bits);
						System.out.println(rs1_bits);
						
						//op code
						pw.print(op_bits);
						
						// adding bits to rs1
						int bit_Num =5;
						
						String zero = String.format("%32s", rs1_bits).replace(' ', '0');
						//System.out.println(zero);
						//System.out.println(zero.length());
						if (zero.length()>bit_Num)
						{
							String binary= zero;
							binary= binary.substring(binary.length() - bit_Num);
							
							System.out.println(binary+"\n");
							pw.print("\t"+binary);
						}
						
						// rs2
						String rs2_bits ="00000";
						pw.print("\t"+rs2_bits);
						
						// imm
						String imm_bits ="0000000000000000";
						pw.print("\t"+imm_bits+"\n");
					}
					// If the line contains a ()/ (load/store)-----------------------------------------------------------------------------
					if (line.contains("(")==true) 
					{
						String source[] = split[2].split(","); // r1,-16(r2)  ---  16(r1),r2
						if (source[0].contains("(")==false)// r1 
						{
							String rs2 = source[0].replaceAll("[^0-9]", ""); //split rs2
							String reg[] = source[1].split(Pattern.quote("("));
							String imm = reg[0];//imm
							String rs1 = reg[1].replaceAll("[^0-9]", "");// rs1
					
							String op_bits = BinaryOp_bits(inst);
							String rs1_bits= Integer.toBinaryString(Integer.parseInt(rs1));
							String rs2_bits = Integer.toBinaryString(Integer.parseInt(rs2));
							String imm_bits = Integer.toBinaryString(Integer.parseInt(imm));
				
							System.out.println(op_bits);
							System.out.println(rs1);
							System.out.println(rs1_bits);
							System.out.println(rs2);
							System.out.println(rs2_bits);
							System.out.println(imm);
							System.out.println(imm_bits);
							
							//op code
							pw.print(op_bits);
							
							// adding bits to rs1
							int bit_Num =5;
							
							String zero = String.format("%32s", rs1_bits).replace(' ', '0');
							//System.out.println(zero);
							//System.out.println(zero.length());
							if (zero.length()>bit_Num)
							{
								String binary= zero;
								binary= binary.substring(binary.length() - bit_Num);
								
								System.out.println(binary+"\n");
								pw.print("\t"+binary);
							}
							
							// adding bits to rs2						
							String zero2 = String.format("%32s",rs2_bits).replace(' ', '0');
							//System.out.println(zero);
							//System.out.println(zero.length());
							if (zero2.length()>bit_Num)
							{
								String binary= zero2;
								binary= binary.substring(binary.length() - bit_Num);
								
								System.out.println(binary+"\n");
								pw.print("\t"+binary);
							}
							
							// adding bits to imm	
							int bit_Num2 = 16;
							String zero3 = String.format("%32s",imm_bits).replace(' ', '0');
							//System.out.println(zero);
							//System.out.println(zero.length());
							if (zero3.length()>bit_Num)
							{
								String binary= zero3;
								binary= binary.substring(binary.length() - bit_Num2);
								
								System.out.println(binary+"\n");
								pw.print("\t"+binary+"\n");
							}
						}
						else // 16(r1)
						{
							String reg[] = source[0].split(Pattern.quote("("));
							String imm = reg[0];
							String rs1 = reg[1].replaceAll("[^0-9]", "");
							String rs2 =  source[1].replaceAll("[^0-9]", "");
							
							String op_bits = BinaryOp_bits(inst);
							String rs1_bits= Integer.toBinaryString(Integer.parseInt(rs1));
							String rs2_bits = Integer.toBinaryString(Integer.parseInt(rs2));
							String imm_bits = Integer.toBinaryString(Integer.parseInt(imm));
							
							System.out.println(rs1);
							System.out.println(rs1_bits);
							System.out.println(rs2);
							System.out.println(rs2_bits);
							System.out.println(imm);
							System.out.println(imm_bits);
							
							//op_code
							pw.print(op_bits);
							
							// adding bits to rs1
							int bit_Num =5;
							
							String zero = String.format("%32s", rs1_bits).replace(' ', '0');
							//System.out.println(zero);
							//System.out.println(zero.length());
							if (zero.length()>bit_Num)
							{
								String binary= zero;
								binary= binary.substring(binary.length() - bit_Num);
								
								System.out.println(binary+"\n");
								pw.print("\t"+binary);
							}
							
							// adding bits to rs2
							String zero2 = String.format("%32s", rs2_bits).replace(' ', '0');
							//System.out.println(zero);
							//System.out.println(zero.length());
							if (zero2.length()>bit_Num)
							{
								String binary= zero2;
								binary= binary.substring(binary.length() - bit_Num);
								
								System.out.println(binary+"\n");
								pw.print("\t"+binary);
							}
							
							// adding bits to imm
							int bit_Num2 = 16;
							String zero3= String.format("%32s", imm_bits).replace(' ', '0');
							//System.out.println(zero);
							//System.out.println(zero.length());
							if (zero3.length()>bit_Num2)
							{
								String binary= zero3;
								binary= binary.substring(binary.length() - bit_Num2);
								
								System.out.println(binary+"\n");
								pw.print("\t"+binary+"\n");
							}	
						}
					}
					// nummComm = 1 (LHI/ branch)--------------------------------------------------------------------------------------
					String shift_type= "lhi";
					
					if (inst.equals(shift_type)) 
					{						
						String source[] = split[2].split(",");
						String rs2 = source[0].replaceAll("[^0-9]", "");
						String imm= source[1]; 
						System.out.println(rs2);
						System.out.println(imm+"\n");
						
						String op_bits = BinaryOp_bits(inst);
						String rs2_bits = Integer.toBinaryString(Integer.parseInt(rs2));
						String imm_bits = Integer.toBinaryString(Integer.parseInt(source[1]));
						//int c_bits = Integer.parseInt(c);
						
						// op_code
						System.out.println("\n"+op_bits+"\n");
						pw.print(op_bits);
						
						//rs1
						String rs1_bits ="00000";
						System.out.println(rs1_bits);
						pw.print("\t"+rs1_bits);
						
						// adding bits to rs2
						int bit_Num = 5;
						String zero= String.format("%32s", rs2_bits).replace(' ', '0');
						//System.out.println(zero);
						//System.out.println(zero.length());
						if (zero.length()>bit_Num)
						{
							String binary= zero;
							binary= binary.substring(binary.length() - bit_Num);
							
							System.out.println("\n"+binary+"\n");
							pw.print("\t"+binary);
						}	
						
						// adding bits to imm
						int bit_Num2= 16;
						String zero2= String.format("%32s", imm_bits).replace(' ', '0');
						//System.out.println(zero);
						//System.out.println(zero.length());
						if (zero2.length()>bit_Num2)
						{
							String binary= zero2;
							binary= binary.substring(binary.length() - bit_Num2);
							
							System.out.println("\n"+binary+"\n");
							pw.print("\t"+binary+"\n");
						}	
					}
					
					String branch_type[] = {"beqz","bnez"};
					if (Arrays.asList(branch_type).contains(inst)) 
					{
						String reg[] = split[2].split(",");
						String rs1 = reg[0].replaceAll("[^0-9]", "");
						String offset_lable = reg[1];
						System.out.println(rs1);
						System.out.println(offset_lable);
						
						int lable_position =searchLable(offset_lable);
						System.out.println(lable_position);
						
						String op_bits = BinaryOp_bits(inst);
						System.out.println(op_bits);
						pw.print(op_bits);
						
						System.out.println("checking address here:"+addressOfline);
						
						//int offset = lable_position-(addressOfline+4);
						int offset = (addressOfline-(lable_position+1))*4;
						System.out.println("offset:"+offset);
						String binaryOffset = Integer.toBinaryString(offset);
						//System.out.println(binaryOffset);
						
						//adding bits to offset
						int bit_Num = 26;
						
						String zero = String.format("%32s", binaryOffset).replace(' ', '0');
						//System.out.println(zero3.length());
						if (zero.length()>bit_Num)
						{
							String binary = zero;
							binary= binary.substring(binary.length() - bit_Num);
							
							System.out.println(binary+"\n");
							pw.print("\t"+binary+"\n");
						}			
					}
							
					//-----------------------------------------------------------------------------------------------------------------
					if(numComma == 2)
					{
						String source[] = split[2].split(",");
						String rs1 = source[1].replaceAll("[^0-9]", "");
						String rs2 = source[0].replaceAll("[^0-9]", "");
						String imm= source[2];
						System.out.println(source[0]);
						System.out.println(source[1]);
						System.out.println(source[2]+"\n");
						
						String op_bits = BinaryOp_bits(inst);
						String rs1_bits = Integer.toBinaryString(Integer.parseInt(rs1));
						String rs2_bits = Integer.toBinaryString(Integer.parseInt(rs2));
						String imm_bits = Integer.toBinaryString(Integer.parseInt(imm));
						
						System.out.println(rs2);
						System.out.println(rs2_bits);
						System.out.println(rs1);
						System.out.println(rs1_bits);
						System.out.println(imm);
						System.out.println(imm_bits);
						
						// op_code
						System.out.println("\n"+op_bits);
						pw.print(op_bits);
						
						// adding bits to rs1
						int bit_Num = 5;
						String zero= String.format("%32s", rs1_bits).replace(' ', '0');
						//System.out.println(zero);
						//System.out.println(zero.length());
						if (zero.length()>bit_Num)
						{
							String binary= zero;
							binary= binary.substring(binary.length() - bit_Num);
							
							System.out.println("\n"+binary+"\n");
							pw.print("\t"+binary);
						}	
						
						// adding bits to rs2
						String zero2= String.format("%32s", rs2_bits).replace(' ', '0');
						//System.out.println(zero2);
						//System.out.println(zero2.length());
						if (zero2.length()>bit_Num)
						{
							String binary= zero2;
							binary= binary.substring(binary.length() - bit_Num);
							
							System.out.println(binary+"\n");
							pw.print("\t"+binary);
						}	
						
						// adding bits to imm
						int bit_Num2 = 16;
						String zero3= String.format("%32s", imm_bits).replace(' ', '0');
						//System.out.println(zero);
						//System.out.println(zero.length());
						if (zero3.length()>bit_Num2)
						{
							String binary= zero3;
							binary= binary.substring(binary.length() - bit_Num2);
							
							System.out.println(binary+"\n");
							pw.print("\t"+binary+"\n");
						}	
					}
				}
					
	//========================================================================================================================	
				if(Arrays.asList(R_type).contains(inst))
				{				
					String source[] = split[2].split(","); // split: rd,rs1,rs2 
					String rd=source[0].toLowerCase();  // dest
					String rs1 = source[1].toLowerCase();// source1
					String rs2= source[2].toLowerCase(); // source2
					System.out.println(rd);
					System.out.println(rs1);
					System.out.println(rs2+"\n");
					
					String op_bits ="000000";
					pw.print(op_bits);
					
					//split rs1 ------------------------------------------------------------------------------------------------
					String reg_split[] = source[1].split("r");
					String s = reg_split[1];
					String suffix = Integer.toBinaryString((Integer.parseInt(s)));
					System.out.println(s+"\n"+suffix);
					
					// adding bits to rs1
					int bit_Num =5;
					
					String zero = String.format("%32s", suffix).replace(' ', '0');
					//System.out.println(zero);
					//System.out.println(zero.length());
					if (zero.length()>bit_Num)
					{
						String binary_rs1 = zero;
						binary_rs1 = binary_rs1.substring(binary_rs1.length() - bit_Num);
						
						System.out.println(binary_rs1+"\n");
						pw.print("\t"+binary_rs1);
					}
				    
				    //split rs2 ------------------------------------------------------------------------------------------------
					String reg2_split[] = source[2].split("r");
					String s2 = reg2_split[1];
					String suffix2 = Integer.toBinaryString((Integer.parseInt(s2)));
					System.out.println(s2+"\n"+suffix2);
					
					//add bits to rs2				
					String zero2 = String.format("%32s", suffix2).replace(' ', '0');
					//System.out.println(zero2.length());
					if (zero2.length()>bit_Num)
					{
						String binary_rs2 = zero2;
						binary_rs2 = binary_rs2.substring(binary_rs2.length() - bit_Num);
						
						System.out.println(binary_rs2+"\n");
						pw.print("\t"+binary_rs2);
					}
					
					//split rd---------------------------------------------------------------------------------------------------
					String reg3_split[] = source[0].split("r");
					String s3 = reg3_split[1];
					String suffix3 = Integer.toBinaryString((Integer.parseInt(s3)));
					System.out.println(s3+"\n"+suffix3);
					
					//add bits to rs2				
					String zero3 = String.format("%32s", suffix3).replace(' ', '0');
					//System.out.println(zero3.length());
					if (zero3.length()>bit_Num)
					{
						String binary_rd = zero3;
						binary_rd = binary_rd.substring(binary_rd.length() - bit_Num);
						
						System.out.println(binary_rd+"\n");
						pw.print("\t"+binary_rd);
					}
			
					// func-------------------------------------------------------------------------------------------------------
					String sub=BinaryFunc_bits(inst);
					System.out.println(sub+"\n");
					pw.print("\t"+sub+"\n");				
				}
			}
			else
			{
				String ln[]=buff.split(" ");
				String inst=ln[0].toLowerCase();
				System.out.println(inst);
				
				if(Arrays.asList(I_type).contains(inst))
				{
					// --------------------------------------------------------------------------------------------------------------------
					if (numComma == 0) // jr r1 (jump)
					{
						String source =ln[1].replaceAll("[^0-9]", "");
						System.out.println(source);
						
						String op_bits = BinaryOp_bits(inst);
						String rs1_bits = Integer.toBinaryString(Integer.parseInt(source));
						System.out.println(op_bits);
						System.out.println(rs1_bits);
						
						//op code
						pw.print(op_bits);
						
						// adding bits to rs1
						int bit_Num =5;
						
						String zero = String.format("%32s", rs1_bits).replace(' ', '0');
						//System.out.println(zero);
						//System.out.println(zero.length());
						if (zero.length()>bit_Num)
						{
							String binary= zero;
							binary= binary.substring(binary.length() - bit_Num);
							
							System.out.println(binary+"\n");
							pw.print("\t"+binary);
						}
						
						// rs2
						String rs2_bits ="00000";
						pw.print("\t"+rs2_bits);
						
						// imm
						String imm_bits ="0000000000000000";
						pw.print("\t"+imm_bits+"\n");
					}
					
					// If the line contains a ()/ (load/store)-----------------------------------------------------------------------------
					if (ln[1].contains("(")==true) 
					{
						String source[] = ln[1].split(","); // r1,-16(r2)  ---  16(r1),r2
						if (source[0].contains("(")==false)// r1 
						{
							String rs2 = source[0].replaceAll("[^0-9]", ""); //split rs2
							String reg[] = source[1].split(Pattern.quote("("));
							String imm = reg[0];//imm
							String rs1 = reg[1].replaceAll("[^0-9]", "");// rs1
					
							String op_bits = BinaryOp_bits(inst);
							String rs1_bits= Integer.toBinaryString(Integer.parseInt(rs1));
							String rs2_bits = Integer.toBinaryString(Integer.parseInt(rs2));
							String imm_bits = Integer.toBinaryString(Integer.parseInt(imm));
				
							System.out.println(op_bits);
							System.out.println(rs1);
							System.out.println(rs1_bits);
							System.out.println(rs2);
							System.out.println(rs2_bits);
							System.out.println(imm);
							System.out.println(imm_bits);
							
							//op code
							pw.print(op_bits);
							
							// adding bits to rs1
							int bit_Num =5;
							
							String zero = String.format("%32s", rs1_bits).replace(' ', '0');
							//System.out.println(zero);
							//System.out.println(zero.length());
							if (zero.length()>bit_Num)
							{
								String binary= zero;
								binary= binary.substring(binary.length() - bit_Num);
								
								System.out.println(binary+"\n");
								pw.print("\t"+binary);
							}
							
							// adding bits to rs2						
							String zero2 = String.format("%32s",rs2_bits).replace(' ', '0');
							//System.out.println(zero);
							//System.out.println(zero.length());
							if (zero2.length()>bit_Num)
							{
								String binary= zero2;
								binary= binary.substring(binary.length() - bit_Num);
								
								System.out.println(binary+"\n");
								pw.print("\t"+binary);
							}
							
							// adding bits to imm	
							int bit_Num2 = 16;
							String zero3 = String.format("%32s",imm_bits).replace(' ', '0');
							//System.out.println(zero);
							//System.out.println(zero.length());
							if (zero3.length()>bit_Num)
							{
								String binary= zero3;
								binary= binary.substring(binary.length() - bit_Num2);
								
								System.out.println(binary+"\n");
								pw.print("\t"+binary+"\n");
							}
						}
						else // 16(r1)
						{
							String reg[] = source[0].split(Pattern.quote("("));
							String imm = reg[0];
							String rs1 = reg[1].replaceAll("[^0-9]", "");
							String rs2 =  source[1].replaceAll("[^0-9]", "");
							
							String op_bits = BinaryOp_bits(inst);
							String rs1_bits= Integer.toBinaryString(Integer.parseInt(rs1));
							String rs2_bits = Integer.toBinaryString(Integer.parseInt(rs2));
							String imm_bits = Integer.toBinaryString(Integer.parseInt(imm));
							
							System.out.println(rs1);
							System.out.println(rs1_bits);
							System.out.println(rs2);
							System.out.println(rs2_bits);
							System.out.println(imm);
							System.out.println(imm_bits);
							
							//op_code
							pw.print(op_bits);
							
							// adding bits to rs1
							int bit_Num =5;
							
							String zero = String.format("%32s", rs1_bits).replace(' ', '0');
							//System.out.println(zero);
							//System.out.println(zero.length());
							if (zero.length()>bit_Num)
							{
								String binary= zero;
								binary= binary.substring(binary.length() - bit_Num);
								
								System.out.println(binary+"\n");
								pw.print("\t"+binary);
							}
							
							// adding bits to rs2
							String zero2 = String.format("%32s", rs2_bits).replace(' ', '0');
							//System.out.println(zero);
							//System.out.println(zero.length());
							if (zero2.length()>bit_Num)
							{
								String binary= zero2;
								binary= binary.substring(binary.length() - bit_Num);
								
								System.out.println(binary+"\n");
								pw.print("\t"+binary);
							}
							
							// adding bits to imm
							int bit_Num2 = 16;
							String zero3= String.format("%32s", imm_bits).replace(' ', '0');
							//System.out.println(zero);
							//System.out.println(zero.length());
							if (zero3.length()>bit_Num2)
							{
								String binary= zero3;
								binary= binary.substring(binary.length() - bit_Num2);
								
								System.out.println(binary+"\n");
								pw.print("\t"+binary+"\n");
							}	
						}
					}
					
					// nummComm = 1 (LHI/ branch)--------------------------------------------------------------------------------------
					String shift_type= "lhi";
					
					if (inst.equals(shift_type)) 
					{						
						String source[] = ln[1].split(",");
						String rs2 = source[0].replaceAll("[^0-9]", "");
						String imm= source[1]; 
						System.out.println(rs2);
						System.out.println(imm+"\n");
						
						String op_bits = BinaryOp_bits(inst);
						String rs2_bits = Integer.toBinaryString(Integer.parseInt(rs2));
						String imm_bits = Integer.toBinaryString(Integer.parseInt(source[1]));
						//int c_bits = Integer.parseInt(c);
						
						// op_code
						System.out.println("\n"+op_bits+"\n");
						pw.print(op_bits);
						
						//rs1
						String rs1_bits ="00000";
						System.out.println(rs1_bits);
						pw.print("\t"+rs1_bits);
						
						// adding bits to rs2
						int bit_Num = 5;
						String zero= String.format("%32s", rs2_bits).replace(' ', '0');
						//System.out.println(zero);
						//System.out.println(zero.length());
						if (zero.length()>bit_Num)
						{
							String binary= zero;
							binary= binary.substring(binary.length() - bit_Num);
							
							System.out.println("\n"+binary+"\n");
							pw.print("\t"+binary);
						}	
						
						// adding bits to imm
						int bit_Num2= 16;
						String zero2= String.format("%32s", imm_bits).replace(' ', '0');
						//System.out.println(zero);
						//System.out.println(zero.length());
						if (zero2.length()>bit_Num2)
						{
							String binary= zero2;
							binary= binary.substring(binary.length() - bit_Num2);
							
							System.out.println("\n"+binary+"\n");
							pw.print("\t"+binary+"\n");
						}	
					}
					
					String branch_type[] = {"beqz","bnez"};
					if (Arrays.asList(branch_type).contains(inst)) 
					{
						String reg[] = ln[1].split(",");
						String rs1 = reg[0].replaceAll("[^0-9]", "");
						String offset_lable = reg[1];
						System.out.println(rs1);
						System.out.println(offset_lable);
						
						int lable_position =searchLable(offset_lable);
						System.out.println(lable_position);
						
						//op code
						String op_bits = BinaryOp_bits(inst);
						System.out.println(op_bits);
						pw.print(op_bits);
						
						// rs1
						String rs1_bits = Integer.toBinaryString(Integer.parseInt(rs1));
						
						//adding bits to label offset
						int bit_Num = 5;
						
						String zero = String.format("%32s", rs1_bits).replace(' ', '0');
						//System.out.println(zero3.length());
						if (zero.length()>bit_Num)
						{
							String binary = zero;
							binary= binary.substring(binary.length() - bit_Num);
							
							System.out.println(binary+"\n");
							pw.print("\t"+binary);
						}
						
						//rs2
						String rs2_bits = "00000";
						pw.print("\t"+rs2_bits);
						
						
						System.out.println("checking address here:"+addressOfline);
						
						//int offset = lable_position-(addressOfline+4);
						int offset = (addressOfline-(lable_position+1))*4;
						System.out.println("offset:"+offset);
						String binaryOffset = Integer.toBinaryString(offset);
						//System.out.println(binaryOffset);
						
						//adding bits to label offset
						int bit_Num2 = 16;
						
						String zero2 = String.format("%32s", binaryOffset).replace(' ', '0');
						//System.out.println(zero3.length());
						if (zero2.length()>bit_Num2)
						{
							String binary = zero2;
							binary= binary.substring(binary.length() - bit_Num2);
							
							System.out.println(binary+"\n");
							pw.print("\t"+binary+"\n");
						}			
					}
					//-----------------------------------------------------------------------------------------------------------------
					if(numComma == 2)
					{
						String source[] = ln[1].split(",");
						String rs1 = source[1].replaceAll("[^0-9]", "");
						String rs2 = source[0].replaceAll("[^0-9]", "");
						String imm= source[2];
						System.out.println(source[0]);
						System.out.println(source[1]);
						System.out.println(source[2]+"\n");
						
						String op_bits = BinaryOp_bits(inst);
						String rs1_bits = Integer.toBinaryString(Integer.parseInt(rs1));
						String rs2_bits = Integer.toBinaryString(Integer.parseInt(rs2));
						String imm_bits = Integer.toBinaryString(Integer.parseInt(imm));
						
						System.out.println(rs2);
						System.out.println(rs2_bits);
						System.out.println(rs1);
						System.out.println(rs1_bits);
						System.out.println(imm);
						System.out.println(imm_bits);
						
						// op_code
						System.out.println("\n"+op_bits);
						pw.print(op_bits);
						
						// adding bits to rs1
						int bit_Num = 5;
						String zero= String.format("%32s", rs1_bits).replace(' ', '0');
						//System.out.println(zero);
						//System.out.println(zero.length());
						if (zero.length()>bit_Num)
						{
							String binary= zero;
							binary= binary.substring(binary.length() - bit_Num);
							
							System.out.println("\n"+binary+"\n");
							pw.print("\t"+binary);
						}	
						
						// adding bits to rs2
						String zero2= String.format("%32s", rs2_bits).replace(' ', '0');
						//System.out.println(zero2);
						//System.out.println(zero2.length());
						if (zero2.length()>bit_Num)
						{
							String binary= zero2;
							binary= binary.substring(binary.length() - bit_Num);
							
							System.out.println(binary+"\n");
							pw.print("\t"+binary);
						}	
						
						// adding bits to imm
						int bit_Num2 = 16;
						String zero3= String.format("%32s", imm_bits).replace(' ', '0');
						//System.out.println(zero);
						//System.out.println(zero.length());
						if (zero3.length()>bit_Num2)
						{
							String binary= zero3;
							binary= binary.substring(binary.length() - bit_Num2);
							
							System.out.println(binary+"\n");
							pw.print("\t"+binary+"\n");
						}	
					}
					//above type here
					
				}//Stop here
				
				//========================================================================================================================	
				if(Arrays.asList(R_type).contains(inst))
				{				
					String source[] = ln[1].split(","); // split: rd,rs1,rs2 
					String rd=source[0].toLowerCase();  // dest
					String rs1 = source[1].toLowerCase();// source1
					String rs2= source[2].toLowerCase(); // source2
					System.out.println(rd);
					System.out.println(rs1);
					System.out.println(rs2+"\n");
					
					String op_bits ="000000";
					pw.print(op_bits);
					
					//split rs1 ------------------------------------------------------------------------------------------------
					String reg_split[] = source[1].split("r");
					String s = reg_split[1];
					String suffix = Integer.toBinaryString((Integer.parseInt(s)));
					System.out.println(s+"\n"+suffix);
					
					// adding bits to rs1
					int bit_Num =5;
					
					String zero = String.format("%32s", suffix).replace(' ', '0');
					//System.out.println(zero);
					//System.out.println(zero.length());
					if (zero.length()>bit_Num)
					{
						String binary_rs1 = zero;
						binary_rs1 = binary_rs1.substring(binary_rs1.length() - bit_Num);
						
						System.out.println(binary_rs1+"\n");
						pw.print("\t"+binary_rs1);
					}
				    
				    //split rs2 ------------------------------------------------------------------------------------------------
					String reg2_split[] = source[2].split("r");
					String s2 = reg2_split[1];
					String suffix2 = Integer.toBinaryString((Integer.parseInt(s2)));
					System.out.println(s2+"\n"+suffix2);
					
					//add bits to rs2				
					String zero2 = String.format("%32s", suffix2).replace(' ', '0');
					//System.out.println(zero2.length());
					if (zero2.length()>bit_Num)
					{
						String binary_rs2 = zero2;
						binary_rs2 = binary_rs2.substring(binary_rs2.length() - bit_Num);
						
						System.out.println(binary_rs2+"\n");
						pw.print("\t"+binary_rs2);
					}
					
					//split rd---------------------------------------------------------------------------------------------------
					String reg3_split[] = source[0].split("r");
					String s3 = reg3_split[1];
					String suffix3 = Integer.toBinaryString((Integer.parseInt(s3)));
					System.out.println(s3+"\n"+suffix3);
					
					//add bits to rs2				
					String zero3 = String.format("%32s", suffix3).replace(' ', '0');
					//System.out.println(zero3.length());
					if (zero3.length()>bit_Num)
					{
						String binary_rd = zero3;
						binary_rd = binary_rd.substring(binary_rd.length() - bit_Num);
						
						System.out.println(binary_rd+"\n");
						pw.print("\t"+binary_rd);
					}
			
					// func-------------------------------------------------------------------------------------------------------
					String sub=BinaryFunc_bits(inst);
					System.out.println(sub+"\n");
					pw.print("\t"+sub+"\n");				
				}

	//========================================================================================================================	
				
				if(Arrays.asList(J_type).contains(inst)) 
				{
					String offset_lable= ln[1]; // split: offset
					System.out.println(offset_lable);
					
					int lable_position =searchLable(offset_lable);
					System.out.println(lable_position);
					
					String op_bits = BinaryOp_bits(inst);
					System.out.println(op_bits);
					pw.print(op_bits);
					
					System.out.println("checking address here:"+addressOfline);
					
					//int offset = lable_position-(addressOfline+4);
					int offset = (addressOfline-(lable_position+1))*4;
					System.out.println("offset:"+offset);
					String binaryOffset = Integer.toBinaryString(offset);
					//System.out.println(binaryOffset);
					
					//adding bits to offset
					int bit_Num = 26;
					
					String zero = String.format("%32s", binaryOffset).replace(' ', '0');
					//System.out.println(zero3.length());
					if (zero.length()>bit_Num)
					{
						String binary = zero;
						binary= binary.substring(binary.length() - bit_Num);
						
						System.out.println(binary+"\n");
						pw.print("\t"+binary+"\n");
					}
				}
			}
//----------------------------------------------------------------------------------			
		//addressOfline = addressOfline+4;
		System.out.println("address:"+addressOfline+"\n"+"---------------------------------------");
		}while(buff!=null);
		
		file_input.close();
		pw.close();
	 }

static String BinaryOp_bits(String inst)
	{
		String bits = " ";
		
		ArrayList<String> inst_list = new ArrayList<String>();
		inst_list.add("j");
		inst_list.add("jal");
		inst_list.add("beqz");
		inst_list.add("bnez");
		inst_list.add("addi");
		inst_list.add("addui");
		inst_list.add("subi");
		inst_list.add("subui");
		inst_list.add("lhi");
		inst_list.add("jr");
		inst_list.add("jalr");
		inst_list.add("seqi");
		inst_list.add("snei");
		inst_list.add("slti");
		inst_list.add("sgti");
		inst_list.add("slei");
		inst_list.add("sgei");
		inst_list.add("lb");
		inst_list.add("lh");
		inst_list.add("lw");
		inst_list.add("lbu");
		inst_list.add("lhu");
		inst_list.add("sb");
		inst_list.add("sh");
		inst_list.add("sw");
		inst_list.add("sequi");
		inst_list.add("sneui");
		inst_list.add("sltui");
		inst_list.add("sgtui");
		inst_list.add("sleui");
		inst_list.add("sgeui");
		inst_list.add("slli");
		inst_list.add("srli");
		inst_list.add("sral");
				
		int[] opBits_list = new int[34];
		opBits_list[0] = 2;
		opBits_list[1] = 3;
		opBits_list[2] = 4;
		opBits_list[3] = 5;
		opBits_list[4] = 8;
		opBits_list[5] = 9;
		opBits_list[6] = 10;
		opBits_list[7] = 11;
		opBits_list[8] = 15;
		opBits_list[9] = 18;
		opBits_list[10] = 19;
		opBits_list[11] = 24;
		opBits_list[12] = 25;
		opBits_list[13]= 26;
		opBits_list[14] = 27;
		opBits_list[15] = 28;
		opBits_list[16] = 29;
		opBits_list[17] = 32;
		opBits_list[18] = 33;
		opBits_list[19] = 35;
		opBits_list[20] = 36;
		opBits_list[21] = 37;
		opBits_list[22] = 40;
		opBits_list[23] = 41;
		opBits_list[24] = 43;
		opBits_list[25] = 48;
		opBits_list[26] = 49;
		opBits_list[27] = 50;
		opBits_list[28] =51;
		opBits_list[29]=52;
		opBits_list[30] =53;
		opBits_list[31] = 54;
		opBits_list[32] = 55;
		opBits_list[33] = 56;

		int position = inst_list.indexOf(inst);
		
		bits = Integer.toBinaryString(opBits_list[position]);
		
		//adding bits
		int bit_Num = 6; 
				
		String zero = String.format("%32s", bits).replace(' ', '0');
		if (zero.length()>bit_Num)
		{
			String binary = zero;
			binary= binary.substring(binary.length() - bit_Num);
		
			return binary;
		}  
		return inst;
	}

static String BinaryFunc_bits(String inst)
	{
		String bits = " ";
		
		ArrayList<String> func_list = new ArrayList<String>();
		func_list.add("sll");
		func_list.add("srl");
		func_list.add("sra");
		func_list.add("sltu");
		func_list.add("sgtu");
		func_list.add("sleu");
		func_list.add("sgeu");
		func_list.add("add");
		func_list.add("addu");
		func_list.add("sub");
		func_list.add("subu");
		func_list.add("seq");
		func_list.add("sne");
		func_list.add("slt");
		func_list.add("sgt");
		func_list.add("sle");
		func_list.add("sge");
		
		int[] funcBits_list = new int[34];
		funcBits_list[0] = 4;
		funcBits_list[1] = 6;
		funcBits_list[2] = 7;
		funcBits_list[3] = 18;
		funcBits_list[4] = 19;
		funcBits_list[5] = 20;
		funcBits_list[6] = 21;
		funcBits_list[7] = 32;
		funcBits_list[8] = 33;
		funcBits_list[9] = 34;
		funcBits_list[10] = 35;
		funcBits_list[11] = 40;
		funcBits_list[12] = 41;
		funcBits_list[13] = 42;
		funcBits_list[14] = 43;
		funcBits_list[15] = 44;
		funcBits_list[16] = 45;

		int position = func_list.indexOf(inst);
		
		bits = Integer.toBinaryString(funcBits_list[position]);
		
		//adding bits
		int bit_Num = 11; 
		
		String zero = String.format("%32s", bits).replace(' ', '0');
		if (zero.length()>bit_Num)
		{
			String binary = zero;
			binary= binary.substring(binary.length() - bit_Num);
			
			return binary;
		}  
		return inst;		
	}
	
static int searchLable(String searchWord) throws IOException
	{
		int countLine = 96;
		String colon = ":";
		String word = (searchWord+colon).trim();
		
		BufferedReader file_input = new BufferedReader(new FileReader("test_file.txt"));
		String buff = " ";
		
		do
		{
			buff = file_input.readLine();
			
			countLine = countLine+4;
			boolean nextWord = buff.contains(word);
			if (nextWord == true) break;	
		}while(buff!=null);
		
		file_input.close();
		return countLine;
	}
}
