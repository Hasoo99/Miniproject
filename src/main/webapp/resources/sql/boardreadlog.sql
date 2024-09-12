select * from sky.boardreadlog;
insert into boardreadlog (readWho, boardNo) values('127.0.0.1', 24);

select datediff(now(),'2024-09-05') as 'day';


-- ipAddr의 유저가 boardNo글을 언제 조회했는지 날짜 차이 계산
select readWho, readWhen, datediff(now(), readWhen), boardNo
from boardreadlog
where readWho = '127.0.0.1' and boardNo=24;

select readWho, readWhen, timediff(now(), readWhen), boardNo
from boardreadlog
where readWho = '127.0.0.1' and boardNo=24;

select readWhen from boardreadlog
where boardNo = 24;

-- 1) ipAddr의 유저가 24번 글을 언제 조회했는지
select readWhen from boardreadlog
where readWho = '127.0.0.1' and boardNo = 24;

-- 한번도 2번글을 '127.0.0.1' 가 조회한 적이 없음. (최초조회)
select readWhen from boardreadlog
where readWho = '127.0.0.1' and boardNo = 2;

-- 2) 1)에서 나온 결과가 null이면 insert
insert into boardreadlog(readWho, boardNo) values(?, ?);

-- 3) 1)에서 나온 결과가 null이 아니면...
select readWhen from boardreadlog
where readWho = ? and boardNo = ?;

select ifnull(datediff(now(), (select readWhen from boardreadlog where readWho = '127.0.0.1' and boardNo = 24)), -1)
as datediff;
-- ipAddr의 유저가 boardNo글을 언제 조회했는지 날짜차이(단, 조회한 적이 없다면 -1반환
select ifnull(datediff(now(), (select readWhen from boardreadlog where readWho = ? and boardNo = ?)), -1)
as datediff;

-- 조회수 증가 (update)
-- update hboard set readcount = readcount + 1 where boardNo = #{boardNo};

-- 아이피주소가 boardNo번 글을 읽은 시간을 테이블에 수정 (update)
update boardreadlog set readWhen = now()
where readWho = '127.0.0.1'
and boardNo = 24;

update boardreadlog set readWhen = now()
where readWho = #{readWho}
and boardNo = #{boardNo};