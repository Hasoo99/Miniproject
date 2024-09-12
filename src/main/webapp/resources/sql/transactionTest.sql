use sky;
select * from tbl_a;
select * from tbl_b;

delete from tbl_a;
delete from tbl_b;
commit;
-- insert into tbl_a values(#{data});