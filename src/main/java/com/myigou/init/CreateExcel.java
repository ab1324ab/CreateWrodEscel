package com.myigou.init;

import com.myigou.tool.PropertiesTool;
import com.myigou.view.WindowView;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by ab1324ab on 2017/4/25.
 */
public class CreateExcel {
    //读取所有配置项
    private static Map<String,String> contentMap=new HashMap<String, String>();
    private static FileOutputStream os=null;
    public CreateExcel() throws Exception{
        String runPath=new File(System.getProperty("user.dir")).getParent();
        //System.out.println("runPath"+runPath);

    }
    public static String mainCreate(Map<String,Object> controlMap){
        try {
            contentMap= WindowView.contentMap;
            CreateExcel testExcel=new CreateExcel();
            String runPath=System.getProperty("user.dir");
            //String runPath=new File(System.getProperty("user.dir")).getParent();
            Cell cell=null;
            Workbook wb = new XSSFWorkbook(CreateExcel.class.getClass().getResourceAsStream("/template.xlsx"));//获取表格模板
            page1(wb,(boolean[])controlMap.get("hiddenPage1"));//第一页
            page2(wb,(boolean[])controlMap.get("jCheckBoxs"));//第二页
            page3(wb,(boolean[])controlMap.get("hiddenPage3"));//第三页
            SimpleDateFormat sdf=new SimpleDateFormat("MM月dd日");
            String tite=sdf.format(new Date());
            File file =new  File(runPath+"\\"+contentMap.get("fileName")+tite+"周计划总结.xlsx");//创建输出文件
            if(!file.exists()){
               // System.out.println("文件不存在！创建");
                file.createNewFile();
            }
            //System.out.println("文件存在！创建");
            os= new FileOutputStream(file);
            wb.write(os);
            return "写出成功！";
        }catch (Exception e){
            return e.getMessage();
        }finally {
            try {
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
                return "文件被占用，请关闭Excel！";

            }
        }

    }

    /**
     * 第一页excel
     * @param wb
     */
      public static void  page1(Workbook wb ,boolean[] hidden){
          Cell cell=null;
          Sheet sheet = wb.getSheetAt(1);//创建表格页
          SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
          Calendar calendar =Calendar.getInstance();
          calendar.setTime(new Date());
          //本周计划- 部门
          cell=sheet.getRow(3).getCell(1);
          cell.setCellValue(contentMap.get("department"));
          //本周计划- 计划人
          cell=sheet.getRow(3).getCell(3);
          cell.setCellValue(contentMap.get("name"));
          //本周计划- 计划日期
          cell=sheet.getRow(3).getCell(5);
          cell.setCellValue(sdf.format(new Date()));
          //本周总结- 部门
          cell=sheet.getRow(3).getCell(8);
          cell.setCellValue(contentMap.get("department"));
          //本周总结- 总结人
          cell=sheet.getRow(3).getCell(10);
          cell.setCellValue(contentMap.get("name"));
          //本周总结- 总结时间
          cell=sheet.getRow(3).getCell(12);
          cell.setCellValue(sdf.format(calendar.getTime()));
          String followingWeek=contentMap.get("pageContents1");
          String[] lienNumber=followingWeek.split(PropertiesTool.SGMTA_SPLIT_LATER);
          int cellRow=0;
          for (int liens=0;liens<lienNumber.length;liens++){
              cellRow=0;
              for(int row=0;row<=8;row++){
                 String tableCell=lienNumber[liens].split(PropertiesTool.SGMTA_SPLIT)[row];
                  if(cellRow==2){
                      cellRow+=2;
                  }else if(cellRow==8){
                          cellRow+=3;
                      }
                  cell=sheet.getRow(5+liens).getCell(cellRow);
                  if(!hidden[liens]){
                      if(cellRow==5||cellRow==6||cellRow==12||cellRow==13){
                          cell.setCellValue(Double.parseDouble(tableCell));
                      }else{
                          cell.setCellValue(tableCell);
                      }
                  }
                  cellRow++;
              }
          }
      }

    /**
     * 第二页
     * @param wb
     */
      public static void page2(Workbook wb,boolean[] hidden) throws ParseException {
          Cell cell=null;
          SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
          Sheet sheet = wb.getSheetAt(2);//创建表格页
          //部门
          cell=sheet.getRow(2).getCell(1);
          cell.setCellValue(contentMap.get("department"));
          //姓名
          cell=sheet.getRow(2).getCell(4);
          cell.setCellValue(contentMap.get("name"));
          //日期
          cell=sheet.getRow(2).getCell(6);
          cell.setCellValue(sdf.format(new Date()));
         //写入任务到数组
          String[] lien =new String[7];
          //boolean[] hidden=new boolean[]{false,false,false,false,false,true,true};
          for(int tableLien=0;tableLien<7;tableLien++){
             if(hidden[tableLien]){
                 if(tableLien!=5&&tableLien!=6){
                     lien[tableLien]=contentMap.get("content"+tableLien).split(PropertiesTool.SGMTA_SPLIT)[0];
                 }
              }else{
                  lien[tableLien]=contentMap.get("content"+tableLien);
              }
          }
          for(int tableCell=0;tableCell<lien.length;tableCell++){
              int cellLndex=0;
              for(int tableRow=0;tableRow<=5;tableRow++){
                  if(lien[tableCell]==null||lien[tableCell].split(PropertiesTool.SGMTA_SPLIT).length<=tableRow){
                      continue;
                  }
                  String tableContent=lien[tableCell].split(PropertiesTool.SGMTA_SPLIT)[tableRow];
                if(cellLndex==3){
                    cellLndex+=2;
                }
                  cell=sheet.getRow(tableCell+5).getCell(cellLndex);
                  if(cellLndex==6||cellLndex==7){
                      cell.setCellValue(Double.parseDouble(tableContent));
                  }else{
                      cell.setCellValue(tableContent);
                  }
                  cellLndex++;
              }
          }
      }

    /**
     * 第三页
     * @param wb
     */
    public static void page3(Workbook wb,boolean[] hidden) throws ParseException {
        Cell cell=null;
        Sheet sheet = wb.getSheetAt(3);//创建表格页
        //下周计划
        cell=sheet.getRow(1).getCell(0);
        SimpleDateFormat  sdf=new SimpleDateFormat("MM月dd日");
        Calendar  calendar=Calendar.getInstance( );
        calendar.setTime(new Date());
        int week=calendar.get(Calendar.DAY_OF_WEEK )-1;
        int difference=8- (week==0? 7 :week);
        calendar.add(Calendar.DAY_OF_YEAR,difference);
        String weekStart=sdf.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_YEAR,4);
        String weekEnd=sdf.format(calendar.getTime());
        cell.setCellValue("下周计划（"+weekStart+"—"+weekEnd+"）");
        //部门
        cell=sheet.getRow(2).getCell(1);
        cell.setCellValue(contentMap.get("department"));
        //姓名
        cell=sheet.getRow(2).getCell(3);
        cell.setCellValue(contentMap.get("name"));
        //日期
        cell=sheet.getRow(2).getCell(5);
        SimpleDateFormat  dayMould=new SimpleDateFormat("yyyy/MM/dd");
        String currentDate=dayMould.format(new Date());
        cell.setCellValue(currentDate);
        //任务
        String[] pageContents3=contentMap.get("pageContents3").split(PropertiesTool.SGMTA_SPLIT_LATER);
        for(int i=0;i<pageContents3.length;i++){
            int cells=0;
            for (int tableRow=0;tableRow<5;tableRow++){
                String content=pageContents3[i].split(PropertiesTool.SGMTA_SPLIT)[tableRow];
                if(cells==2){
                    cells+=2;
                }
                cell=sheet.getRow(5+i).getCell(cells);
                if(!hidden[i]){
                    if(tableRow==3||tableRow==4){
                        cell.setCellValue(Double.parseDouble(content));
                    }else{
                        cell.setCellValue(content);
                    }
                }
                cells++;
            }
        }
    }
}
