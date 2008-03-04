/*==============================================================*/
/* DBMS name:      ORACLE Version 10g                           */
/* Created on:     2008-2-29 16:35:03                           */
/*==============================================================*/


alter table SYS_FAVORITE_TYPE
   drop constraint FK_FAV_REF_USER;

alter table SYS_GROUP_ROLES
   drop constraint FK_GROUP_REF_ROLE;

alter table SYS_GROUP_ROLES
   drop constraint FK_ROLE_REF_GROUP;

alter table SYS_RESOURCES
   drop constraint FK_RES_REF_MODULE;

alter table SYS_ROLE_RESOURCES
   drop constraint FK_RESOURCE_REF_ROLE;

alter table SYS_ROLE_RESOURCES
   drop constraint FK_ROLE_REF_RESOURCE;

alter table SYS_USER_FAVORITE
   drop constraint FK_FAV_REF_TYPE;

alter table SYS_USER_FAVORITE
   drop constraint FK_FAVORITE_REF_RES;

alter table SYS_USER_GROUPS
   drop constraint FK_USER_REF_GROUP;

alter table SYS_USER_GROUPS
   drop constraint FK_LOGIN_REF_GROUP;

alter table SYS_USER_ROLES
   drop constraint FK_USER_REF_ROLE;

alter table SYS_USER_ROLES
   drop constraint FK_LOGIN_REF_ROLE;

drop table SYS_FAVORITE_TYPE cascade constraints;

drop table SYS_GROUPS cascade constraints;

drop table SYS_GROUP_ROLES cascade constraints;

drop table SYS_LOGIN_ACCOUNT cascade constraints;

drop table SYS_MODULES cascade constraints;

drop table SYS_RESOURCES cascade constraints;

drop table SYS_ROLES cascade constraints;

drop table SYS_ROLE_RESOURCES cascade constraints;

drop table SYS_USER_FAVORITE cascade constraints;

drop table SYS_USER_GROUPS cascade constraints;

drop table SYS_USER_ROLES cascade constraints;

/*==============================================================*/
/* Table: SYS_FAVORITE_TYPE                                     */
/*==============================================================*/
create table SYS_FAVORITE_TYPE  (
   TYPE_NO              NUMBER(12)                      not null,
   EMP_NO               VARCHAR2(12),
   TYPE_NAME            VARCHAR2(30)                    not null,
   IS_ACTIVE            NUMBER(1)                      default 1 not null
      constraint CKC_IS_ACTIV_SYS_FAVORITE check (IS_ACTIVE between 0 and 1 and IS_ACTIVE in (0,1)),
   constraint PK_SYS_FAVORITE_TYPE primary key (TYPE_NO)
);

comment on table SYS_FAVORITE_TYPE is
'收藏夹分类';

/*==============================================================*/
/* Table: SYS_GROUPS                                            */
/*==============================================================*/
create table SYS_GROUPS  (
   GROUP_NO             NUMBER(4)                       not null,
   GROUP_NAME           VARCHAR2(40),
   "COMMENT"            VARCHAR2(400),
   CREATE_BY            VARCHAR2(12),
   CREATE_DATE          DATE,
   UPDATE_BY            VARCHAR2(12),
   UPDATE_DATE          DATE,
   IS_ACTIVE            NUMBER(1)                      default 1 not null
      constraint CKC_IS_ACTIV_SYS_GROUPS check (IS_ACTIVE between 0 and 1 and IS_ACTIVE in (0,1)),
   constraint PK_SYS_GROUPS primary key (GROUP_NO)
);

comment on table SYS_GROUPS is
'人员组信息';

/*==============================================================*/
/* Table: SYS_GROUP_ROLES                                       */
/*==============================================================*/
create table SYS_GROUP_ROLES  (
   ROLE_NO              NUMBER(4),
   GROUP_NO             NUMBER(4),
   SEQUENCE_NO          NUMBER(12)                      not null,
   CREATE_BY            VARCHAR2(12),
   CREATE_DATE          DATE,
   UPDATE_BY            VARCHAR2(12),
   UPDATE_DATE          DATE,
   IS_ACTIVE            NUMBER(1)                      default 1 not null
      constraint CKC_IS_ACTIV_SYS_GROUP_RO check (IS_ACTIVE between 0 and 1 and IS_ACTIVE in (0,1)),
   constraint PK_SYS_GROUP_ROLES primary key (SEQUENCE_NO)
);

comment on table SYS_GROUP_ROLES is
'组对应角色信息表';

/*==============================================================*/
/* Table: SYS_LOGIN_ACCOUNT                                     */
/*==============================================================*/
create table SYS_LOGIN_ACCOUNT  (
   EMP_NO               VARCHAR2(12)                    not null,
   LOGIN_NAME           VARCHAR2(20)                    not null,
   PASSWORD             VARCHAR2(40),
   CREATE_DATE          DATE,
   CREATE_BY            VARCHAR2(12),
   IS_ACTIVE            NUMBER(1)                      default 1 not null
      constraint CKC_IS_ACTIV_SYS_LOGIN_AC check (IS_ACTIVE between 0 and 1 and IS_ACTIVE in (0,1)),
   UPDATE_DATE          DATE,
   constraint PK_SYS_LOGIN_ACCOUNT primary key (EMP_NO)
);

comment on table SYS_LOGIN_ACCOUNT is
'系统用户帐号表';

/*==============================================================*/
/* Table: SYS_MODULES                                           */
/*==============================================================*/
create table SYS_MODULES  (
   MODULE_NO            NUMBER(6)                       not null,
   MODULE_NAME          VARCHAR2(30)                    not null,
   DESCRIPTION          VARCHAR2(200),
   PAGE_INDEX           NUMBER(6)                       not null,
   PAGE_ICON            VARCHAR2(20),
   PRE_MODULE           NUMBER(6),
   CREATE_DATE          DATE,
   CREATE_BY            VARCHAR2(12),
   IS_SUBSYSTEM         NUMBER(1)                      default 0 not null
      constraint CKC_IS_SUBSY_SYS_MODULES check (IS_SUBSYSTEM between 0 and 1 and IS_SUBSYSTEM in (0,1)),
   IS_ACTIVE            NUMBER(1)                      default 1 not null
      constraint CKC_IS_ACTIV_SYS_MODULES check (IS_ACTIVE between 0 and 1 and IS_ACTIVE in (0,1)),
   UPDATE_DATE          DATE,
   UPDATE_BY            VARCHAR2(12),
   constraint PK_SYS_MODULES primary key (MODULE_NO)
);

comment on table SYS_MODULES is
'系统模块定义,模块分多层';

/*==============================================================*/
/* Table: SYS_RESOURCES                                         */
/*==============================================================*/
create table SYS_RESOURCES  (
   RES_NO               NUMBER(4)                       not null,
   MODULE_NO            NUMBER(6),
   RES_NAME             VARCHAR2(30),
   "COMMENT"            VARCHAR2(200),
   PAGE_INDEX           NUMBER(4)                       not null
      constraint CKC_PAGE_IND_SYS_RESOURCE check (PAGE_INDEX between 0 and 9999),
   RES_VALUE            VARCHAR2(200),
   RES_TYPE             NUMBER(1)                      default 1
      constraint CKC_RES_TYPE_SYS_RESOURCE check (RES_TYPE is null or (RES_TYPE between 1 and 3 and RES_TYPE in (1,2,3))),
   PAGE_ICON            VARCHAR2(30),
   CREATE_DATE          DATE,
   CREATE_BY            VARCHAR2(12),
   UPDATE_DATE          DATE,
   UPDATE_BY            VARCHAR2(12),
   IS_ACTIVE            NUMBER(1)                      default 1 not null
      constraint CKC_IS_ACTIV_SYS_RESOURCE check (IS_ACTIVE between 0 and 1 and IS_ACTIVE in (0,1)),
   constraint PK_SYS_RESOURCES primary key (RES_NO)
);

comment on table SYS_RESOURCES is
'模块对应资源';

/*==============================================================*/
/* Table: SYS_ROLES                                             */
/*==============================================================*/
create table SYS_ROLES  (
   ROLE_NO              NUMBER(4)                       not null,
   ROLE_NAME            VARCHAR2(30)                    not null,
   ROLE_CODE            VARCHAR2(30)                    not null,
   "COMMENT"            VARCHAR2(400),
   FATHER_ROLE          NUMBER(4),
   CREATE_DATE          DATE,
   CREATE_BY            VARCHAR2(12),
   IS_ACTIVE            NUMBER(1)                      default 1 not null
      constraint CKC_IS_ACTIV_SYS_ROLES check (IS_ACTIVE between 0 and 1 and IS_ACTIVE in (0,1)),
   IS_EXTEND            NUMBER(1)                      default 1 not null
      constraint CKC_IS_EXTEN_SYS_ROLES check (IS_EXTEND between 0 and 1 and IS_EXTEND in (0,1)),
   UPDATE_DATE          DATE,
   UPDATE_BY            VARCHAR2(12),
   constraint PK_SYS_ROLES primary key (ROLE_NO)
);

comment on table SYS_ROLES is
'系统角色定义';

/*==============================================================*/
/* Table: SYS_ROLE_RESOURCES                                    */
/*==============================================================*/
create table SYS_ROLE_RESOURCES  (
   SEQUENCE_NO          NUMBER(8)                       not null,
   ROLE_NO              NUMBER(4),
   RES_NO               NUMBER(4),
   CREATE_DATE          DATE,
   CREATE_BY            VARCHAR2(12),
   IS_ACTIVE            NUMBER(1)                      default 1 not null
      constraint CKC_IS_ACTIV_SYS_ROLE_RES check (IS_ACTIVE between 0 and 1 and IS_ACTIVE in (0,1)),
   constraint PK_SYS_ROLE_RESOURCES primary key (SEQUENCE_NO)
);

comment on table SYS_ROLE_RESOURCES is
'角色对应资源信息';

/*==============================================================*/
/* Table: SYS_USER_FAVORITE                                     */
/*==============================================================*/
create table SYS_USER_FAVORITE  (
   FAVORITE_NO          NUMBER(12)                      not null,
   RES_NO               NUMBER(4),
   TYPE_NO              NUMBER(12),
   FAVORITE_NAME        VARCHAR2(40),
   PAGE_INDEX           NUMBER(4)                      default 0
      constraint CKC_PAGE_IND_SYS_USER_FAV check (PAGE_INDEX is null or (PAGE_INDEX between 0 and 200)),
   EMP_NO               VARCHAR2(12),
   CREATE_DATE          DATE                            not null,
   CREATE_BY            VARCHAR2(12),
   UPDATE_BY            VARCHAR2(12),
   UPDATE_DATE          DATE,
   IS_ACTIVE            NUMBER(1)                      default 1 not null
      constraint CKC_IS_ACTIV_SYS_USER_FAV check (IS_ACTIVE between 0 and 1 and IS_ACTIVE in (0,1)),
   constraint PK_SYS_USER_FAVORITE primary key (FAVORITE_NO)
);

comment on table SYS_USER_FAVORITE is
'个人操作收藏夹';

/*==============================================================*/
/* Table: SYS_USER_GROUPS                                       */
/*==============================================================*/
create table SYS_USER_GROUPS  (
   SEQUENCE_NO          NUMBER(12)                      not null,
   GROUP_NO             NUMBER(4),
   EMP_NO               VARCHAR2(12),
   CREATE_BY            VARCHAR2(12),
   CREATE_DATE          DATE,
   UPDATE_BY            VARCHAR2(12),
   UPDATE_DATE          DATE,
   IS_ACTIVE            NUMBER(1)                      default 1 not null
      constraint CKC_IS_ACTIV_SYS_USER_GRO check (IS_ACTIVE between 0 and 1 and IS_ACTIVE in (0,1)),
   constraint PK_SYS_USER_GROUPS primary key (SEQUENCE_NO)
);

comment on table SYS_USER_GROUPS is
'用户对应组信息';

/*==============================================================*/
/* Table: SYS_USER_ROLES                                        */
/*==============================================================*/
create table SYS_USER_ROLES  (
   SEQUENCE_NO          NUMBER(8)                       not null,
   EMP_NO               VARCHAR2(12),
   ROLE_NO              NUMBER(4),
   IS_LOCK              NUMBER(1),
   CREATE_DATE          DATE,
   CREATE_BY            VARCHAR2(12),
   IS_ACTIVE            NUMBER(1)                      default 1 not null
      constraint CKC_IS_ACTIV_SYS_USER_ROL check (IS_ACTIVE between 0 and 1 and IS_ACTIVE in (0,1)),
   constraint PK_SYS_USER_ROLES primary key (SEQUENCE_NO)
);

comment on table SYS_USER_ROLES is
'用户对应角色';

alter table SYS_FAVORITE_TYPE
   add constraint FK_FAV_REF_USER foreign key (EMP_NO)
      references SYS_LOGIN_ACCOUNT (EMP_NO);

alter table SYS_GROUP_ROLES
   add constraint FK_GROUP_REF_ROLE foreign key (ROLE_NO)
      references SYS_ROLES (ROLE_NO);

alter table SYS_GROUP_ROLES
   add constraint FK_ROLE_REF_GROUP foreign key (GROUP_NO)
      references SYS_GROUPS (GROUP_NO);

alter table SYS_RESOURCES
   add constraint FK_RES_REF_MODULE foreign key (MODULE_NO)
      references SYS_MODULES (MODULE_NO);

alter table SYS_ROLE_RESOURCES
   add constraint FK_RESOURCE_REF_ROLE foreign key (ROLE_NO)
      references SYS_ROLES (ROLE_NO);

alter table SYS_ROLE_RESOURCES
   add constraint FK_ROLE_REF_RESOURCE foreign key (RES_NO)
      references SYS_RESOURCES (RES_NO);

alter table SYS_USER_FAVORITE
   add constraint FK_FAV_REF_TYPE foreign key (TYPE_NO)
      references SYS_FAVORITE_TYPE (TYPE_NO);

alter table SYS_USER_FAVORITE
   add constraint FK_FAVORITE_REF_RES foreign key (RES_NO)
      references SYS_RESOURCES (RES_NO);

alter table SYS_USER_GROUPS
   add constraint FK_USER_REF_GROUP foreign key (GROUP_NO)
      references SYS_GROUPS (GROUP_NO);

alter table SYS_USER_GROUPS
   add constraint FK_LOGIN_REF_GROUP foreign key (EMP_NO)
      references SYS_LOGIN_ACCOUNT (EMP_NO);

alter table SYS_USER_ROLES
   add constraint FK_USER_REF_ROLE foreign key (ROLE_NO)
      references SYS_ROLES (ROLE_NO);

alter table SYS_USER_ROLES
   add constraint FK_LOGIN_REF_ROLE foreign key (EMP_NO)
      references SYS_LOGIN_ACCOUNT (EMP_NO);

