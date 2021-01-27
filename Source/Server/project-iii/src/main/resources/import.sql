insert into doctor (name, birth, country, i_card, address, contact, start_working, faculty, hospital, position) values ('Mr. Manh', '1968-12-10', 'Ha Noi', '12037857123', 'Thanh Xuan, Ha Noi', '0867539347', '1996-1-2', 'surgery', 'Hong Ngoc', 'head master')
insert into doctor (name, birth, country, i_card, address, contact, start_working, faculty, hospital, position) values ('Mrs. Hien', '1971-12-11', 'Phu Tho', '98172963181', 'Tay Ho, Ha Noi', '0423793478', '1997-8-23', 'resident', 'VinMec', 'vice president')

insert into drug (name, instruct, daily_using, unit, note) values ('Insulin', 'use once a day', '16:30:00', 'pump', 'many side effects')
insert into drug (name, instruct, daily_using, unit, note) values ('Sulphonylurea', 'use once a day', '16:30:00', 'capsule', 'many side effects')
insert into drug (name, instruct, daily_using, unit, note) values ('Glibenclamid', 'use once a day', '16:30:00', 'mg', 'restricted')
insert into drug (name, instruct, daily_using, unit, note) values ('Gliclazid', 'use once a day', '16:30:00', 'mg', 'None')

insert into patient (name, birth, job, i_card, address, contact, height, weight, sex, country, "begin", start_treatment, usual_symptom, history, type, doctor_id) values ('Huy Binh', '1988-2-10', 'architect', 'icard123', 'Cau Giay', '02498126387', 178, 69, 'yes', ' Ha Noi, Viet Nam', '2019-2-2', '2019-2-2', 'skin dry', 'none history',2, 1)
insert into patient (name, birth, job, i_card, address, contact, height, weight, sex, country, "begin", start_treatment, usual_symptom, history, type, doctor_id) values ('Hoang Trung', '1989-2-10', 'architect', 'icard123', 'Cau Giay', '02498126387', 176, 68, 'yes', ' Ha Noi, Viet Nam', '2019-2-2', '2019-2-2', 'skin dry', 'none history',2, 2)
insert into patient (name, birth, job, i_card, address, contact, height, weight, sex, country, "begin", start_treatment, usual_symptom, history, type, doctor_id) values ('Thanh Tung', '1990-2-10', 'architect', 'icard123', 'Cau Giay', '02498126387', 175, 60, 'yes', ' Ha Noi, Viet Nam', '2019-2-2', '2019-2-2', 'skin dry', 'none history',2, 1)
insert into patient (name, birth, job, i_card, address, contact, height, weight, sex, country, "begin", start_treatment, usual_symptom, history, type, doctor_id) values ('Thuy Linh', '1991-2-10', 'architect', 'icard123', 'Cau Giay', '02498126387', 170, 55, 'no', ' Ha Noi, Viet Nam', '2019-2-2', '2019-2-2', 'skin dry', 'none history',2, 2)


insert into test (time, description, hba1c_index, random_index, craving_index, after_meal_index, patient_id) values ('2020-12-6 19:30:00+7', 'daily test', 5.1, 120.1, 90.5, 121.3, 1)
insert into test (time, description, hba1c_index, random_index, craving_index, after_meal_index, patient_id) values ('2020-12-4 19:30:00+7', 'daily test', 4.5, 130.8, 92.2, 132.3, 1)
insert into test (time, description, hba1c_index, random_index, craving_index, after_meal_index, patient_id) values ('2020-12-1 19:30:00+7', 'daily test', 3.4, 140.1, 93.9, 111.7, 2)
insert into test (time, description, hba1c_index, random_index, craving_index, after_meal_index, patient_id) values ('2020-12-7 19:30:00+7', 'daily test', 2.9, 170.9, 91.1, 110.3, 2)
insert into test (time, description, hba1c_index, random_index, craving_index, after_meal_index, patient_id) values ('2020-12-16 19:30:00+7', 'daily test', 1.7, 111.7, 56.6, 140.8, 3)
insert into test (time, description, hba1c_index, random_index, craving_index, after_meal_index, patient_id) values ('2020-12-23 19:30:00+7', 'daily test', 7.3, 112.3, 43.8, 124.9, 3)
insert into test (time, description, hba1c_index, random_index, craving_index, after_meal_index, patient_id) values ('2020-12-21 19:30:00+7', 'daily test', 4.2, 124.1, 24.4, 123.1, 3)
insert into test (time, description, hba1c_index, random_index, craving_index, after_meal_index, patient_id) values ('2020-12-15 19:30:00+7', 'daily test', 9.3, 125.2, 67.2, 123.2, 4)
insert into test (time, description, hba1c_index, random_index, craving_index, after_meal_index, patient_id) values ('2020-11-19 19:30:00+7', 'daily test', 2.1, 178.1, 88.1, 125.4, 4)
insert into test (time, description, hba1c_index, random_index, craving_index, after_meal_index, patient_id) values ('2020-10-15 19:30:00+7', 'daily test', 9.3, 113.2, 69.8, 159.2, 4)
insert into test (time, description, hba1c_index, random_index, craving_index, after_meal_index, patient_id) values ('2020-11-19 19:30:00+7', 'daily test', 2.1, 98.1, 77.1, 166.1, 1)


insert into prescription (start_timestamp, end_timestamp, expect, note, patient_id, doctor_id) values ('2020-11-19', '2020-12-19', 'all indices decrease', 'using 3 weeks more if indices would not changed', 3, 1)
insert into prescription (start_timestamp, end_timestamp, expect, note, patient_id, doctor_id) values ('2020-09-19', '2020-12-19', 'all indices decrease', 'using 1 week more if indices would not changed', 3, 1)
insert into prescription (start_timestamp, end_timestamp, expect, note, patient_id, doctor_id) values ('2020-12-19', '2020-12-29', 'all indices decrease', 'no further used', 1, 1)
insert into prescription (start_timestamp, end_timestamp, expect, note, patient_id, doctor_id) values ('2020-10-19', '2020-12-19', 'all indices decrease', 'used with doctor instructions', 1, 2)
insert into prescription (start_timestamp, end_timestamp, expect, note, patient_id, doctor_id) values ('2020-11-19', '2020-12-19', 'all indices decrease', 'using 1 month more if indices would not changed', 2, 2)



insert into instruction (duration, dose, content, drug_id, prescription_id) values (10, 1.0, 'use frequently', 2, 1)
insert into instruction (duration, dose, content, drug_id, prescription_id) values (11, 2.0, 'use frequently', 2, 2)
insert into instruction (duration, dose, content, drug_id, prescription_id) values (12, 1.5, 'intensive', 1, 3)
insert into instruction (duration, dose, content, drug_id, prescription_id) values (13, 2.5, 'use frequently', 1, 4)
insert into instruction (duration, dose, content, drug_id, prescription_id) values (14, 1.0, 'use frequently', 1, 5)
insert into instruction (duration, dose, content, drug_id, prescription_id) values (15, 1.0, 'use frequently', 1, 1)


insert into clinic (name, address, contact, open, close, longitude, latitude) values ('Hong Ngoc', 'Hai Ba Trung, Ha Noi', '02334545', '08:00:00', '19:30:00', 105.78390699797596,21.017019440788324)
insert into clinic (name, address, contact, open, close, longitude, latitude) values ('Thanh Chan', 'Hai Ba Trung, Ha Noi', '02334545', '08:00:00', '19:30:00', 105.80359305768842,21.007989490420798)
insert into clinic (name, address, contact, open, close, longitude, latitude) values ('Ky Hung', 'Hai Ba Trung, Ha Noi', '02334545', '08:00:00', '19:30:00', 105.79945191289926,21.01208032541626)


insert into clinic_employee(clinic_id,doctor_id) values (1,1)
insert into clinic_employee(clinic_id,doctor_id) values (1,2)
insert into clinic_employee(clinic_id,doctor_id) values (2,1)
insert into clinic_employee(clinic_id,doctor_id) values (3,1)