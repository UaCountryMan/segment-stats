create table country_stats (
   segment_id integer not null,
   country_code varchar(2) not null,
   active_profiles_amount bigint,
   sleeping_profiles_amount bigint,
   total_profiles_amount bigint,
   total_third_party_profiles bigint,
   PRIMARY KEY (segment_id,country_code)
);
