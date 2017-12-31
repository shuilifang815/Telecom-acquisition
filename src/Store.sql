BEGIN
  FOR i IN 1..31 LOOP
    EXECUTE IMMEDIATE
      'DROP TABLE t_detail_'||TO_CHAR(i);
  END LOOP;
END;
/

BEGIN
  FOR i IN 1..31 LOOP
    EXECUTE IMMEDIATE
      'CREATE TABLE t_detail_'||TO_CHAR(i)||
      '(
        aaa_login_name	VARCHAR2(30),
        login_ip	VARCHAR2(32),
        login_date	DATE,
        logout_date	DATE,
        nas_ip	VARCHAR2(32),
        time_duration	NUMBER(10)
       )';
  END LOOP;
END;
/


END 
