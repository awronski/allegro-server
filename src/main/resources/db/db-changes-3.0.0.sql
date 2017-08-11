-- 3.0.0

ALTER TABLE clients ADD restclientid varchar(256);
ALTER TABLE clients ADD restclientsecret varchar(256);
ALTER TABLE clients ADD restclientapikey varchar(256);
ALTER TABLE clients ADD restredirecturi varchar(256);
