package com.telemed;

import com.telemed.model.*;
import com.telemed.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Controller
public class TelemedController {

    User currentUser = null;


    @Autowired
    UserRepositoryDB userRepository;
    @Autowired
    RecordRepositoryDB recordRepository;



    public TelemedController() {

    }


    @GetMapping("/patients")
    public String showPatients(Model model) {
        model.addAttribute(userRepository.findByType(0));
        model.addAttribute("currentUser", currentUser);
        return "doctor_home.html";
    }

    @GetMapping("/addNewPatient")
    String addNewUser(@RequestParam("fname") String fname, @RequestParam("lname") String lname,
                      @RequestParam("birthday") String birthday, @RequestParam("mbo") int mbo,
                      @RequestParam("email") String email, @RequestParam("password") String password, Model model) throws ParseException {
        userRepository.save(new User(fname, lname, birthday, mbo, email, password));
        return "redirect:/patients";
    }

    @GetMapping("/showEditPatient")
    String showEditUser(int id, Model model) {
        User user = userRepository.findUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("currentUser", currentUser);
        return "doctor_edit_patient.html";
    }

    @GetMapping("/editPatient")
    String editUser(int id, String fname, String lname, String birthday, int mbo, String email, String password, Model model) throws ParseException {
        User user = userRepository.findUserById(id);
        user.setFname(fname);
        user.setLname(lname);
        user.setBirthday(birthday);
        user.setMbo(mbo);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);
        System.out.println("Korisnik " + fname + " " + lname + " je ažuriran.");
        return "redirect:/patients";
    }

    @GetMapping("/showPatientOverview")
    String showPatientOverview(int id, Model model) {
        User user = userRepository.findUserById(id);
        model.addAttribute(recordRepository.findAllByUser(user));
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("user", user);
        return "doctor_patient_overview.html";
    }



    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/loginProcess")
    public String loginProcess(@RequestParam("email") String email,
                               @RequestParam("password") String password, Model model){

        User u = userRepository.findByEmailAndPassword(email, password);

        if (u == null) {
            model.addAttribute("userMessage", "Korisnik nije pronađen!");
            return "login.html";
        } else {
            currentUser = u;

            if (u.getType() == 0) {
                return "redirect:/records";
            } else {
                return "redirect:/patients";
            }
        }
    }


    @GetMapping("/doctorNewPatient")
    public String doctorNewPatient(Model model) {
        model.addAttribute("currentUser", currentUser);
        return "doctor_new_patient.html";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam("id") int id){
        User user = userRepository.findUserById(id);
        List<Record> recordList = recordRepository.findByUser(user);
        recordRepository.deleteAll(recordList);
        userRepository.delete(user);
        return "redirect:/patients";
    }

    @GetMapping("/records")
    public String records(Model model) {
        model.addAttribute("currentUser", currentUser);
        model.addAttribute(recordRepository.findAllByUser(currentUser));
        return "patient_home.html";
    }

    @GetMapping("/addNewRecord")
    String addNewRecord( int sysPressure, int diasPressure, int heartRate, float bodyTemperature,
                         String date, String time, User user) throws ParseException {
        Record newRecord = new Record(sysPressure, diasPressure, heartRate, bodyTemperature, date, time, user);
        newRecord.setUser(currentUser);
        recordRepository.save(newRecord);
        return "redirect:/records";
    }

    @GetMapping("/patientNewRecord")
    public String patientNewData(Model model) {
        model.addAttribute("currentUser", currentUser);
        return "patient_new_record.html";
    }

    @GetMapping("/deleteRecord")
    public String deleteRecord(@RequestParam("id") int id){
        recordRepository.deleteById(id);
        return "redirect:/records";
    }

    @GetMapping("/showEditRecord")
    String showEditRecord(int id, Model model) {
        Record record = recordRepository.findRecordById(id);
        model.addAttribute("record", record);
        model.addAttribute("currentUser", currentUser);
        return "patient_edit_record.html";
    }

    @GetMapping("/editRecord")
    String editRecord(int id, int sysPressure, int diasPressure, int heartRate, float bodyTemperature, String date, String time) throws ParseException {
        Record record = recordRepository.findRecordById(id);
        record.setId(id);
        record.setSysPressure(sysPressure);
        record.setDiasPressure(diasPressure);
        record.setHeartRate(heartRate);
        record.setBodyTemperature(bodyTemperature);
        record.setDate(date);
        record.setTime(time);
        recordRepository.save(record);
        System.out.println("Zapis je ažuriran");
        return "redirect:/records";
    }

    @GetMapping("/searchPatient")
    public String searchPatient(@RequestParam("lname") String lname, Model model) {
        model.addAttribute(userRepository.findByLname(lname));
        model.addAttribute("currentUser", currentUser);
        return "doctor_home.html";
    }

    /*
    public String reformatDate(String dateToReformat) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = sdf.parse(dateToReformat);
        sdf.applyPattern("dd.MM.yyyy.");
        return sdf.format(d);

    }

     */


}