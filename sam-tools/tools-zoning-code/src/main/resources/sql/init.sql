SELECT LEVEL lv ,M.LOCAL_CODE 编码,m.local_name 名称,m.sup_local_code 上级编码,
 SYS_CONNECT_BY_PATH(M.Local_Name, '->') 路径 from tb_location M
START WITH M.sup_local_code='0'
CONNECT BY PRIOR M.LOCAL_CODE = M.Sup_Local_Code
 ORDER SIBLINGS BY M.LOCAL_CODE;