
show catalogs;
show schemas in hive;
show tables in hive.default;

CREATE TABLE hive.default.cs_event (
sid varchar,
pid varchar,
ts varchar,
ip_address varchar,
year varchar,
month varchar,
day varchar,
hour varchar,
event varchar
)
WITH (
  format = 'JSON',
  partitioned_by = ARRAY['year', 'month', 'day', 'hour', 'event'],
  external_location = 'hdfs://localhost:8020/kafka/cs-events'
) ;

use hive.default;
call system.sync_partition_metadata('default', 'cs_event', 'full');

select * from hive.default.cs_event;
/*
 *
 * sid                                 |pid                                 |ts        |ip_address     |year|month|day|hour|event       |
------------------------------------+------------------------------------+----------+---------------+----+-----+---+----+------------+
4c032f24-f0ce-4b63-a21e-b5ab1f553b84|34b51b67-5ffe-4e72-aa2d-5c5552c04403|1529489611|37.79.185.251  |2018|06   |20 |13  |orderSummary|
48029922-7e51-4e7e-9b65-d42f25048740|7707c9cf-a058-4297-b4fb-006e3793989a|1530275599|61.157.57.239  |2018|06   |29 |15  |orderSummary|
4f5f35c2-3706-4117-a8ef-55b1930dda33|b7665864-8641-4cc6-b9e4-02094d969ca8|1530439278|184.0.178.205  |2018|07   |01 |13  |productView |
8d39db56-a9b3-4087-9e26-edb82c8037f2|5d894b9b-fe2e-4e67-9346-1523bf8b256b|1530099703|55.250.246.110 |2018|06   |27 |14  |productView |
68b0078a-b753-456b-9fdc-ec974aa27c16|9bc0a722-11f8-4bd9-b988-7acde02fc8b2|1529167991|178.123.3.205  |2018|06   |16 |19  |newSession  |
d14e761e-214e-4fb4-8b60-a28e040d732c|32e506d0-09f4-4168-a912-5dde272d5a66|1530517140|173.139.172.172|2018|07   |02 |10  |productView |
bb112c06-6574-409c-baa4-38daf1e432c1|c2f9c76d-832b-4a1b-8cbe-f1da15116c3a|1529336016|182.219.255.18 |2018|06   |18 |18  |newSession  |
e4cc79b2-4c48-416a-8538-c94b72f8691a|3852771d-1fb1-4159-9707-ab6f532bd953|1529198678|57.216.12.188  |2018|06   |17 |04  |orderSummary|
55bd7905-1389-4a50-86a5-73402705a25a|83d460b8-b0c3-4636-bd3a-d5d2b418f5a9|1528972697|25.17.139.124  |2018|06   |14 |13  |newSession  |
88625271-a562-44eb-9ae7-66d3d063d16e|9d099a9c-d4d4-40f4-8843-f90fa565d0cc|1530448374|142.27.192.96  |2018|07   |01 |15  |newSession  |
cc26b7f1-d7dc-4151-95cc-fddc85a5b2b7|f26670c4-f71a-4405-aad1-711ff47f45f9|1529334151|21.8.40.135    |2018|06   |18 |18  |productView |
4923b020-5d8e-48bd-ba99-6c946365a0c3|40399381-10af-494e-a1b7-1f33642d674b|1528398287|228.46.156.233 |2018|06   |07 |22  |productView |
02bcbc5e-f874-4733-ab13-120da4e366db|7fe961ff-eb19-488a-b30c-be5fbb0fd66e|1530373842|144.114.153.98 |2018|06   |30 |18  |boutiqueView|
2fba59b8-f1ee-4d2b-aaa4-9b27d5d53817|743a0ed8-8183-4294-8134-71d8cb8f1084|1530638059|122.175.214.225|2018|07   |03 |20  |orderSummary|
a98b3d96-94a0-4e00-999f-7958f83ecb0c|eff59201-f908-4626-a69a-1f12a9d10c53|1529672830|67.238.85.183  |2018|06   |22 |16  |boutiqueView|
75b359d5-1216-4f73-a4bc-fd40200aacb6|e045ad7c-383f-48f7-8858-068a74c241c5|1529380537|126.48.80.230  |2018|06   |19 |06  |boutiqueView|
c77cfb1c-f525-471f-ae09-14103b583451|9af2f281-061c-40a0-9b43-d1b5d0860c19|1529270855|165.126.186.187|2018|06   |18 |00  |orderSummary|
63dc6297-957b-40ef-83d4-8449d00cdcd3|cca5c424-ca81-492d-890e-7a11e823df3a|1528786081|204.126.59.101 |2018|06   |12 |09  |boutiqueView|
fad9e992-b11e-4b6c-b33f-88a29f5dff84|fba0d74d-bc5e-4cb8-a0f0-d5935a2b8106|1528365981|127.230.50.92  |2018|06   |07 |13  |productView |
3ba4772c-ff1d-4004-9d47-b6eb7ee5e42b|cb95a515-fa7a-4bf3-ad44-859f399caffa|1529211253|61.152.81.164  |2018|06   |17 |07  |newSession  |
...
..
.
(truncated output)
*
 */*/