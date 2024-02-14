CLINIC WORK IS THE LATEST VERSION!

----------------------PROJECT ONLINE CLINIC-------------------------

ENTITIES 
1- USERS
2- DEPARTMENTS
3- DOCTORSCHEDULE
4- PATIENT
5-APPOINTMENTS
6-ROLE(ENUM) -- ADMIN, WORKER, DOCTOR
7-GENDER (ENUM) -- MALE, FEMALE
8-BASE ENTITY (PARENT CLASS)



------------------ QUICK EXPLAINATION-------------------------

Aplikacioni eshte per nje klinike online.
Ne klinike kemi punonjes, doktore dhe admini i cili ka sherbime specifike ne klinike.
Si fillim duhet te regjistrojme disa users te klinika jone me qellim qe te mbushim databazen.
Me pas duhet te krijojme departamentet po ashtu per te njejtin qellim.
Dhe me vone do asenjojme doktoret qe kemi krijuar te departamenti perkates.
Supozohet se klinika eshte e hapur cdo dite ne orarin 8:00-17:00, ky orar eshte dhe orari perkates per doktorat e klinikes.
Pacientet na telefonojne dhe kerkojne per takim me doktor specifik.
Si fillim duhet te regjistrojme pacientin me te dhenat qe do na japi apo me pas do i vendosim nje 'Appointment' ne nje orar te caktuar me nje doktor te caktuar.



-------------------------APIs EXPLAINATION--------------------------------

USERCONTROLLER
1 - updateUser - ndryshon te dhenat e userit (mbi kte kan akses vetem admini dhe nje worker.)
2- 
2 - getScheduleOfDoctorWithDoctorId - merr schedulen e nje doktori me id qe i japim ne
3 - assignDoctorToDepartment - assigns doktor te departamenti specifik (mbi kte kan akses vetem admini dhe nje worker.)
4 - getUserById - merr nje user me id specifike.
5 - getUsersOfDepartment - merr te gjith doktoret qe i perkasin nje departamenti (workers nuk kan departament te asenjuar)
6 - getUsers - merr te gjith userat e klinikes
7 - findDoctorWithMostAppointments - liston doktoret qe kan me shume takime (descending)
8 - findUserWithFirstNAme & findUserWithLastName - gjen userat me first dhe last name (nxjerr dhe userat qe permbajne karakteret qe ne po kerkojme)
9 - findDoctorThatPatientVisitMostly - kerkojme id e nje pacienti specifik per te pare cilet doktor ai frekuenton me shpesh (LISTE)
10 - deleteUser - fshin userin (mbi kte kan akses vetem admini dhe nje worker.)
11 - assignScheduleToDoctor - updateos schedulin e nje doktori per oraret e punes qe punon ai.
12 - updatePassword - updates passwordin e userit (ENCODING)
13-changePassword - updaton passwordin e nje useri specifik nga admini (per rastet kur useri ka harruar passwordin)
14-setDaysOffForUser- vendos nje dite pushim per doktorin dhe nuk mund te vendosesh appointment ne ate date
15-deleteDayOffForUser- fshin diten e pushimit

PATIENT CONTROLLER
1-createPatient - krijon nje pacient te ri
2-updatePatient - updates nje pacient
3-getPatientById - gjen nje pacient me id qe i kalojm ne
4- getPatients - gjen te gjith pacientet
5-deletePatient - fshin nje pacient (setDeleted true)
6- performHardDeletion - therret "deleteByDeletedTrue" e cila fshin te gjithe patients qe kan deleted true (hard delete) - ne servis kjo ka skedulim cdo 30 ditor (te API ka akses vtm admini)

DOCTORSCHEDULE CONTROLLER
1-getDoctorScheduleById - gjen schedulen me id specifike


DEPARTMENT CONTROLLER
1- createDepartment - krijon nje departament te ri (vtm admin dhe worker)
2 - updateDepartmentNoUsers - updates departamentin (vetem emrin)
3 - getDepartmentById - gjen departamentin me id specifike
4 - getDepartments - gjen te gjith departamentet
5 - deleteDepartment -- fshin departamentin (soft delete) 

APPOINTMENT CONTROLLER
1-addAppointmentToDoctorSchedules - krijon nje appointment me doktorid, appointment dhe patient id
2-updateAppointmentWithId - updates nje appointment 
3- getAppointmentById - gjen appointmentin me id specifike
4- getPatientAppointments - gjen appointments te pacientit me id specifike
5- getPatientAppointmentsBetweenTheDates - gjen gjith appointments te nje doktori midis dy datave te caktuara
6-getAppointments - liston te gjith appointments

REST AUTH CONTROLLER
1- token - merr nje token jwt nga logimi i suksesshem i nje useri
2 - registerDoctor - regjistron nje doktor (vetem admini)
3 - registerAdmin - regjistron adminin (e kam lene per arsye qe te kem admin me password te enkriptuar)
4- registerWorker - regjistron workerin
