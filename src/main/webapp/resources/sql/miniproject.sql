CREATE TABLE `sky`.`member` (
  `userId` VARCHAR(8) NOT NULL,
  `userPwd` VARCHAR(200) NOT NULL,
  `userName` VARCHAR(12) NULL,
  `mobile` VARCHAR(13) NULL,
  `email` VARCHAR(50) NULL,
  `registerDate` DATETIME NULL DEFAULT now(),
  `userImg` VARCHAR(50) NOT NULL DEFAULT 'avatar.png',
  `userPoint` INT NULL DEFAULT 100,
  PRIMARY KEY (`userId`),
  UNIQUE INDEX `mobile_UNIQUE` (`mobile` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE);
  
  --
  
  CREATE TABLE `sky`.`hboard` (
  `boardNo` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(20) NOT NULL,
  `content` VARCHAR(2000) NULL,
  `writer` VARCHAR(8) NULL,
  `postDate` DATETIME NULL DEFAULT now(),
  `readCount` INT NULL DEFAULT 0,
  `ref` INT NULL DEFAULT 0,
  `step` INT NULL DEFAULT 0,
  `refOrder` INT NULL DEFAULT 0,
  PRIMARY KEY (`boardNo`),
  INDEX `hboard_member_fk_idx` (`writer` ASC) VISIBLE,
  CONSTRAINT `hboard_member_fk`
    FOREIGN KEY (`writer`)
    REFERENCES `sky`.`member` (`userId`)
    ON DELETE SET NULL
    ON UPDATE NO ACTION)
COMMENT = '계층형게시판';

-- 회원 가입
insert into member(userId, userPwd, userName, mobile, email)
values('tosimi', sha1(md5('1234')), '토심이', '010-2222-6666', 'tosimi@abc.com');
insert into member(userId, userPwd, userName, mobile, email)
values('tomoong', sha1(md5('1234')), '토뭉이', '010-2222-8888', 'tomoong@abc.com');

select * from member;

-- 게시판에 게시글 등록
insert into hboard(title, content, writer)
values ('안녕하세요', '반갑습니다 여러분', 'tosimi');
insert into hboard(title, content, writer)
values ('햄스터 분양하고싶어요', '골든햄스터', 'tomoong');

select * from hboard order by boardNo desc;

select * from hboard order by boardNo desc;

-- insert into hboard (title, content, writer)
values (#{title}, #{content}, #{writer});

--

CREATE TABLE `sky`.`pointdef` (
  `pointWhy` VARCHAR(20) NOT NULL,
  `pointScore` INT NULL,
  PRIMARY KEY (`pointWhy`))
COMMENT = '유저에게 적립할 포인트에 대한 정책 정의 테이블\n어떤 사유로 몇 포인트를 지급하는지에 대해 정의';

-- 

CREATE TABLE `sky`.`pointlog` (
  `pointLogNo` INT NOT NULL AUTO_INCREMENT,
  `pointWho` VARCHAR(8) NOT NULL,
  `pointWhen` DATETIME NULL DEFAULT now(),
  `pointWhy` VARCHAR(20) NOT NULL,
  `pointScore` INT NOT NULL,
  PRIMARY KEY (`pointLogNo`),
  INDEX `pointlog_member_fk_idx` (`pointWho` ASC) VISIBLE,
  CONSTRAINT `pointlog_member_fk`
    FOREIGN KEY (`pointWho`)
    REFERENCES `sky`.`member` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
COMMENT = '어떤 멤버에게 어떤 사유로 몇 포인트가 언제 지급되었는지 기록 테이블';

-- 글 작성시 멤버에게 포인트 로그를 저장하는 쿼리문
insert into pointlog(pointWho, pointWhy, pointScore)
values('tosimi', '글작성', (select pointScore from pointdef where pointwhy = '글작성'));
-- 멤버에게 지급된 point를 더해서 수정하는 쿼리문
update member
set userPoint = userPoint + (select pointScore from pointdef where pointwhy = '글작성')
where userId = 'tosimi';

select * from member;
select * from hboard;
select * from pointlog;
