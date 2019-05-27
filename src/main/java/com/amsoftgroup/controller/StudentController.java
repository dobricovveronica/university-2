package com.amsoftgroup.controller;

import com.amsoftgroup.model.*;
import com.amsoftgroup.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.validation.Valid;
import java.util.*;

@RestController
public class StudentController extends HttpServlet{

    @Resource
    private StudentService studentService;

    List<Student> studentsList = new ArrayList<>();


    @RequestMapping(value = "/")
    public ModelAndView findAll(@ModelAttribute(value = "studentFilter") StudentFilter studentFilter,
                                @RequestParam(defaultValue = "1", value = "page", required = false) Integer page,
                                @RequestParam(defaultValue = "5", value = "size", required = false) Integer size, BindingResult bindingResult
    ) {
        List<Discipline> disciplines = studentService.findAllDisciplines();
        List<Group> groups = studentService.findAllGroups();

        if (studentFilter.getGender() == null) {

            studentFilter.setGender('T');
        }
        int start = (page == null) ? 0 : (page - 1) * size;
        double nrOfPages = (double) studentService.countOfStudents(studentFilter) / size;
        int maxPages = (int) (((nrOfPages > (int) nrOfPages) || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages);

        List<Student> students = studentService.findStudents(studentFilter, start, size);
        studentsList = studentService.findStudentsList(studentFilter);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("students", students);
        modelAndView.addObject("disciplines", disciplines);
        modelAndView.addObject("groups", groups);
        modelAndView.addObject("studentFilter", studentFilter);
        modelAndView.addObject("maxPages", maxPages);
        modelAndView.addObject("page", page);
        modelAndView.setViewName("list");
        modelAndView.setStatus(HttpStatus.OK);
        return modelAndView;
    }

    @GetMapping(value = "/RESET")
    public ModelAndView search() {
        studentsList = new ArrayList<>();
        return new ModelAndView("redirect:/", "studentFilter", new StudentFilter());
    }

    @GetMapping(value = "/EDIT")
    public ModelAndView editStudent(@RequestParam(value = "id") Long id) {
        Student student = new Student();
        List<Group> groups = studentService.findAllGroups();
        List<PhoneType> phoneTypes = studentService.findAllPhoneTypes();
        ModelAndView modelAndView = new ModelAndView();
        if (id != 0) {
            student = studentService.findStudentByIdSetPhone(id);
        }
        if (modelAndView.isEmpty()) {
            modelAndView.addObject("student", student);
        }
        modelAndView.addObject("groups", groups);
        modelAndView.addObject("phoneTypes", phoneTypes);
        modelAndView.setViewName("student");
        modelAndView.setStatus(HttpStatus.OK);
        return modelAndView;
    }


    @RequestMapping(value = "/ADDEDIT")
    public void addEditStudent(@ModelAttribute(value = "student") @Valid Student student, BindingResult bindingResult) {

        if (student.getId() == 0) {
            LibraryAbonament libraryAbonament = new LibraryAbonament();
            libraryAbonament.setStatus("None");
            student.setLibraryAbonament(libraryAbonament);
            List<Average> averages = new ArrayList<>();
            Set<Discipline> disciplines = new HashSet<>();
            student.setAverages(averages);
            student.setDisciplines(disciplines);
            String imageBase64 = "/9j/4AAQSkZJRgABAQAAAQABAAD//gA7Q1JFQVRPUjogZ2QtanBlZyB2MS4wICh1c2luZyBJSkcgSlBFRyB2NjIpLCBxdWFsaXR5ID0gOTAK/9sAQwADAgIDAgIDAwMDBAMDBAUIBQUEBAUKBwcGCAwKDAwLCgsLDQ4SEA0OEQ4LCxAWEBETFBUVFQwPFxgWFBgSFBUU/9sAQwEDBAQFBAUJBQUJFA0LDRQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQU/8AAEQgCWAJYAwEiAAIRAQMRAf/EAB8AAAEFAQEBAQEBAAAAAAAAAAABAgMEBQYHCAkKC//EALUQAAIBAwMCBAMFBQQEAAABfQECAwAEEQUSITFBBhNRYQcicRQygZGhCCNCscEVUtHwJDNicoIJChYXGBkaJSYnKCkqNDU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6g4SFhoeIiYqSk5SVlpeYmZqio6Slpqeoqaqys7S1tre4ubrCw8TFxsfIycrS09TV1tfY2drh4uPk5ebn6Onq8fLz9PX29/j5+v/EAB8BAAMBAQEBAQEBAQEAAAAAAAABAgMEBQYHCAkKC//EALURAAIBAgQEAwQHBQQEAAECdwABAgMRBAUhMQYSQVEHYXETIjKBCBRCkaGxwQkjM1LwFWJy0QoWJDThJfEXGBkaJicoKSo1Njc4OTpDREVGR0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3h5eoKDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uLj5OXm5+jp6vLz9PX29/j5+v/aAAwDAQACEQMRAD8A+2KKKKAE/ipaKKAE6UtIvSloAKKKKACiikIzQAtFFIvSgBaKT+KloAKKTbS0AFFFFABRTKdtoAWikXpS0AJzS0UUAIc0c0daWgBP1paKKACiiigAooooAKKKKACiiigAooooAKKKKACik/ipaAAd6KKKACiiigAooooAKMmiigAooooAKKKKACiiigBF6UtFFACN0paKTigBaKRulLQAUUUUAFFFFABRRRQAUUUUAFFFFACbqOlLSbaAAdTRSgYIooAKKKKACiiigBG6UbaWigBGGKNtLRQAm2gjNBGaWgApMcUtFABSE4oXpS0AJtpaQjNLQAgGKNtH8NLQAjdKNtNp9ABRSfw0tABSbaWigBP4aWkbpRjigBB1o706k20ALRRRQAUUUUAFFFFABRRRQAUnWj+GjbQAtFFFABRRRQAZ60UUUAFFFFACbaWiigAooooAKT1o6UtABRRRQAUi9KWigApNtLRQAjdKP4qCM0tABRSfjRtoAWk20tFABRRSbaAFoopP4aAFopNtLQAUjdKG6UtABSZ7UdKWgAHUUUg6rRQAtFFFABRSbqWgAooooAKKKKACiiigBNtLRRQAUUnSloAKKKKACjHNI3SloAKKKKACiik/hoAWiiigAooooAKKKKACiiigAooooAKKKKAE/ipaKKACiiigBOaWiigAooooAKKKKACiik20AHSloooAKKKT8KABulLRRQAUUUUAFJtpaKACkbpS0UAFFFFABRRSbaAFooooAKKTpR1oAWiiigAooooAMc0jdKB16UtABRRRQAL2ooooAKT+KlpNtAC0UUUAFFFFABRRRQAnSlo4/GigAooooAKKKKACiiigAooooAKKKKACiiigAHeiiigAooooAKTpS0UAFJtpaKACiiigAooooAKKKKACiiigAooooAKKKKAE3UtFFABRRRQAUUUUAFFFI3SgBaTdS0UAFJupaKACiiigApDwaWigAooooAKKKKACkbpTuPekoAKKKKACk/ClooATrS0UUAFFFFABRRRQADrRQOtFACfxUL0o5o3UALRSZ4NG6gBaKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKT+GloAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooATdS0UUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAA60UDrRQAHrSL0paKACiik60ALRRR/EKACiiigApOtH8NLQAUUUUAFFFFABRRRQAUUjdKWgApP4qWigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAB1ooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAAdaKB1FFABRSN0paAE20tFIeooAOtLRRQAUUUUAFFFFABRRRQAm6loooAKKKKACiiigAooooAKKT+KjrQAtFFIvSgA3UtFFABRRRQAgOaWiigAooooAKKBk9AT9K07Pw1qV/jyrSTB/iYYFAGZRXVW3w51KUjzXihH13VpxfC8D/W3ueP4VxQBwWc0V6NH8MbMA77qUnP8IFO/wCFY2P/AD8z/pQB5tn1pa9Df4Y2275LuQD3AzVOf4YShSYr1Tjsy0AcRRXSXPw/1WHlUjlH+y3NYt3pd3Yn/SLaSIdMstAFWijv0ooAKKKKAE3UtFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFJ9aAFooooAKKKKACiiigAooooAB1FFA60UAJ0paKKAEHU0bqF6UtABRRSN0oAWik6UtABRRRQAUUUUAFJtpaKAE/hpaKKACiiigAooooAKT+KlooAKKKKACiiigAooooAKP51oaToN5rL4toiyjguegr0HQPAtnpiLJdKtzcep+6PoKAOA0zw5f6rIFht2C93YYArsNN+GsEfzXkxkP91OBXbBAqgAYA7AU7AoAz7HQ7HTgPIto0I/ixz+dX9tOooAbtp1FFABRRRQAUmBS0UAJx7Ux0DqVYAg8EHpUlFAGHqHg/TNRB326xsf4oxg1yuqfDeaHe9lKJVzkRtwcV6L2pNtAHht3p9zYsVnheMg4yw4qCvc7qyhvIjHPEsqHswrh9f+HfzmXTSAveFj/KgDhKKlu7SaxmaKeMxyDqpqKgAo/SiigAooooAKKKKACiiigAooooAKKKKAF496SiigAooooAKKKKACiiigAooooAKKQnFLQAUUUUAA6iigdaKACiiigBAMUfxUcY4o6GgBaKQHNLQAUUm6loAKKKT+GgBaKKKACiikbpQAtFFFABRRRQAUUUUAFFFFACbqOnalooAKRulLT4YHuZliiUvIxwFHegBiqXYBRknoBXZ+GPAb3YW51AGOPOVi7t9fStrwr4Mj0wLcXaLJc9geQtdYAMUARW1pDaRCOGNYkH8KipdtL2paACiiigAooooAKKKKACiiigApMilooAKKKKACiiigBCM0m2nUUAZesaBZ6zCyXEY3kYEgHzCvM/EHhW60KZjjzbb+GUf19K9fIzUU0MdxG0cih0bgqwyDQB4VRXXeLPBb6cWurNS1uOWQdVrkPrQAucdqKKKACiiigAooooAKTdS0UAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQADqKKB1FFACfxU2n0UANzxigcml3UL0oAWiiigAooooAKKKKACiiigAooooAKKKKADpiiik3UALRRRQAUUUm6gBaRelLQoLEAAknsKAHQwPcypFEpeRzgKOpr1Xwl4Wi0W2WWVQ944yzEfd9hVTwV4VGmwJd3C/6U4yF/uiutXrQAuBS0UUAFFFFABRSdqWgBMClopD0oAWiiigBMCloooAKKKKACiikHSgAwKWiigAooooATAowKWigBjqsgKsAQeCDXnnjbwh9lJvrKP8AdH/WRqPu+4r0amOgdSpGQRg0AeD0V1HjTwsdIm+1W65tnPOP4TXL0AFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQADrRQOoooAD1pF6UtJjigAHU0elC9KOlAC0UUh6igAbpS0UUAFFFFABRRRQAUUUUAI3SlopOaAFooooAKTdS0UAFFFJuoAWuw8CeFxfSi+uUPkp/q1I4Y+tc5ommPq+pQ2y9GPzH0XvXstjZx2FtHbxLtjjGBQBOBinUUUAFFFFABRSZFLQAUUUh6UAB6UtJkUtABRRRQAUUUUAFJ3paKACiiigBB0paKKAEPSloooAKKKKACiik6igCvfWUWoWrwToJInGCDXj+v6HLoeoPA2TH1jc/xCvaO1c94w0Bda01mVf9IiBZD6+1AHk1FIQVJGMEHpS0AFFFFABRRRQAUUUUAFFFFABRRRQAUUUn4UAHBpaKKACk20tFABRRRQAUUmfaloAB1FFA60UAFJtpaTdQAtFMp38VAB1obpS0i9KAFopePem7aAFooooAT+KloooAKKKKACiiigAooooAKKTbR1oAP4qWir2haa+q6rBboPvNlj6AUAd/4C0FbCwF5ImLiYZGey111RxRCKNUHRRgVJQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFJ1FLRQB5X470FdL1AXEK7YZ8n6N3rmK9g8W6U2r6LNEv31+dfqK8fIKsVPBHGKACiiigAooooAKKKKACiiigAPWiiigAz7UUUUAFFFFABRRRQAUUUUAFFFFAAOtFA60UAFJ1paKAE6ij+KlooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACk20tFACfxV33wz03C3F6xOT+7Udvf+lcFgk4HOa9k8MWP9n6LaxEYbbuP1NAGvRRRQAUUUUAFFFFABSd6WigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKTvS0UAFFFFACHpS0UUAFFFFACHkV474t006XrlxGCSjHepPvXsWRXCfE2x3RWt2FztJjY/XkUAcBRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFJupaACiiigAHUUUDqKKACiiigBlO/ioXpR/FQAtJuo60tABRRRQAUUUUAA4oooHOKACiiigAooooAKTpS0UAFFFFAF3RbQ3uq2sIP3nFe1ooRVA6AYryrwBbGfxDG3/ADyUv9O39a9YoAKKKQdKAAdKWiigAooooAKKKKACkyKWigAooooAKKTvS0AFFFFABRRRQAUUUUAJ0FLRRQAmRS0UUAFFJ3paACiiigAoopO9AB2rF8X2X23QLpB95V3Dj0rbqC7j860mj/vIR+lAHhQ6UtPuI/JnkQ/wsVplABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFAAOoooHUUUAFJ/DS0UAIvSj+KhelLQAUUUUAFJ/DS0UAFFJtpaACiiigBG6UL0paKACiiigAooooAKKKKAO0+GMO69u5eOEC+/J/+tXo9cD8L4wU1B/4gUH6Gu+oAKKKKACiiigAooooAKKKKACiiigBB0paKKACiiigAooooAKKKKACiiigAooooAKKKKACiikyKAFpO1HPtS0AFFFFABSHpS01utAHiuvxeRrN4gxxIelUK1/F6CPxHfAdN+f0rIoAKKTdS0AFFJzRk+tAC0UUUAFFFFABRSfw0tACfxUtFFABRRRQAUUUmeaAFooooAB1FFA60UAFI3SlooAKTbRuoXpQAtFFJ0oAP4aWiigAooooAKKKKACiiigAoooPFABRRRQAUUUUAd/8Lv8AUah/vJ/Jq7yvPvhi5D3yZ4O0ke4zXoNABSZFLRQAUUUUAIelGRS0UAFFFFABRRRQAUUmRS0AFFFFABRRRQAUUUUAFFFFABRRRQAUmeM0tFACZFLRRQAUUUnUUALRRRQAUUU09BQB4/4y/wCRlvv9/wDpWL+FavilzJ4gvSTk7+tZdABSbaATS0AJtpaKRulAC0UUUAFJtpV96KACk20bqWgBOtLSL0paAA9qKKKACiik/ioAWiiigAHWigdRRQAUUUEUAFFJ/FQDmgBaKKKAEXpS0UUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAdb8NrgR6xLGf44jj6givTq8d8HXAtvEVoxIAJ2n8a9gJzQA6iiigAooooAKKKKACiiigAooooAKKKKACik/LFLQAUUnajvQAtFFFABSYFLSYFAC0UUUAFFJ1FLQAUUUUAFFFFABRRRQAUx22KSeg5px6VS1icW2l3Mh/hQ0AeN6pL5+pXL9MyN/Oq1BJYknqeaKACiigHFABSN0paTbQAtJ1paKACiiigBF6UtFFABRRRQAUUUUAFJ/FS0UAFFFFAAOoooHWigAooooATrS0UUAFFFFABRSN0obpQAtFFIvSgBaKKKACiiigAooooAKKKKACiiigCS2mNvcRyjqjBuK9usrkXdpDMp3K6g5rw2vU/AGofbdEWMkl4TsOfSgDqKKKKACiiigAooooAKKTvS0AIelLRRQAUUneloAKKKKACiiigAoopO9AC0UnaloAKKTvS0AFJ2paKACiiigBOopaKTIoAWiik6CgBD0Fcv8AEK9FroZiz80zBce1dQ3WvNPiPqP2jU4rYE7YVyQfU/8A6qAORooooAKKKKACkbpS0jdKAFooooAKKTbRg96AFooooAKKTpS0AFFJzS0AFJupaKACiiigAHaigdaKACik60tABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABXUfD/VRYawYXYiOcbRzxu7f1rl6dFK0MqOpwVIINAHu+6l6Cs/RdRTVNOguEOdy8+x71o0AFFFFABRRRQAUnrS0mBQAtMp9FACdTS0UUAFFFFABRRRQAUUUUAFFFFABRRRQAmRR2oPSloAKKTApaAEPSlopD0oAbT6KKAK93cpZW0k8hARFJJPFeKajdm/vp7hiSXYnk9q9A+Iuqrb6elmrfvJTkgHoBXm5xmgApOlLRQAnWloooAKQnmhulKBQAnfpS0UUAJ0o3UtFABSfw0DqaWgAooooAKTHNLRQAnSjdS0UAA70m6looAB1HFFA6iigBF6UtFFABSfxUtFACdaWiigBOlLRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAmeKUDOaKKAOt8Aa+1nfCxlf8AcS/dz2avTAea8HR2idXQlXU5BHavXPCWvLrenIWbNzGNsg9/WgDeooooAKKKKACiiigAooooAKKTqKWgApB0paKAEPSlpMiloAZT6KKACiiigAooooAKKKKACiiigApD0paKACq93cpZW0k0jBUQbjVivPfiD4i3sNOgk4H+tx/KgDk9Z1aXWdQkuZT1OFA7DsKpUUhGaAFopP4aWgAooooAKKKB1oAKKKKACk60tJzQAtFFFABSbqWigBPwo3UtFABSfxUtFABRRRQAg6milXtRQAUUUUAJjmloooAKKRelLQAjdKWiigAooooAKKKKACiiigAooooAKKKT+KgBaKKKACiiigArQ0PWZtEvknjYhc4dexFZ9J1oA9x06/h1K0juIG3RyAEe3tVuvJPCXid9DuBHKSbRz8wH8PvXqlvPHcwrLE4eNxlWB4IoAnooooATIpaKKACk/ClooAKKKTIoAOgpab0NOoAKKTtS0AFJ3paKACiiigApPwpaQdKAFooooAKKKKACikyKyte1yDQ7NppWBc5CIDyxoAp+LvEa6HYlY2BupBhB6e9eUSSNLIzuxZ2OST1NWNT1KbVbpp523Of0FVaACik3UufegAopAc0fxUALSdKWkXpQAtFFJuoAWik/io/ioAWiiigAopM5FLQAUUnNLQAUUUm6gBaKTdS0AFFFFAAOtFC/1ooAKKKMYoAKKKKACiiigAooooAKKKKACl496SigAooooAKKKKACiiigAooooAKKKKACiiigBNtdN4U8YS6Ky20/7y0J7nlPpXNUUAe6W11FdRLJE6ujcgg1NkV5B4c8VXOgyhf9bbH70Z/pXp+k6za6xbCW3kDDup6j60AaNFJkUtABRRRQAUUUUAFFFFABRRRQAmBS0UUAJ0FLRRQAUUUUAFFFN3UAOopMiuc8SeMLbRFaNCJrojhAeB9aAL2va/b6HbNJKwMn8MYPJNeU63rdxrl2Z5246Kg6KKh1HU59UuTPcvvc/kKrUAFFFFABSfw0tJ70ALRSfxUtABRRRQAUUnWloAKKKT+dAC0UUUAFFI3SloATqKWk20tACfw0tFFABRSdaNtAC0UUUAA60UDg0UAFFJ1paAE6UtJtpaACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKtadqdzpVws1tIUYEEjsfrVRulW7LSrvUmAtrd5cnGQOKAPQvD/j22vwI7zbbT9Ac/K3+FdWkiyKGVgynoRzmvP9K+G0rlHvpgi4yY4+T+ddtpelxaRaiCEsUHOWOTQBeoopMCgBaKKKACiiigApMilooAQdKMilooATApaKKACkwKWk6igAJxVa9v7ewiMlxKsSDuxqyRmue8Q+D4deYyNNJHLj5ecqPwoA5vxD8QXn32+njbGRgzHqfp6VxckjSuXdi7sclj1Nb+q+B9S035kj+0x+sfUfhWA8bRMVdSrDggigBKKRelLQAUUi9KWgAooooAKKKKACik20bqAFoopOpoAWiiigAopNtLQAn8NLRRQAUn8NLRQAUUUUAFFFIBigBaKKKAAHkUUDqKKACiiigAooooATpS0UUAFFFFABRRRQAUUUUAI3SloooAKKKKACiiigAooooAKKKKACiipLa2lu5VjhjaRzxhRmgCOrmm6LeatKEtoWcd2xwPxrstB+HgRlm1Fg/fyV6fjXbW9tFaxiOGNY4x0VRgUAchovw7gt9sl+/nP18tfuiuwt7WK1iEcKLGg6KoxUuOMUYFACbadRRQAh6UdRR0FGBQAtFFFABRRRQAUUUUAFFFFABSdRS0UAJ1FLRRQAUhGaWigBvFZWreG7HWFPnwgP2kUYatekwKAPLta8A3lhue1/0mH0H3hXLvG0TlXUqw6gjmveNtZOteGrLW4mEsYWUj5ZVHzCgDxtulDdK39e8H3mi/OB58GfvoOn1rAbpQAtJ/FS0UAIvSlopG6UALRRRQAUnQ0tFABRRRQAUUUUAFFFFABRRSbqADdS0UUAI3SjdS0UAFIvSloJoABjNFA6iigAooooAKTbS0UAIvSjdS0UAJ0pTxRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUKrOwVQSScYFdt4V8CNPtutQG1DysPc/WgDE0Dwnd65IGA8q37yt/SvTNH0Cz0SLZbR4J6u3JNX4okgQJGoRRxgCpOgoAMCgDFLSZFAC0h6UtFABSHpS0UAFFFJ2oAWiikHSgA6iloooATj2paKTIoAWkyKWkyKAFoopOooAWiiigAooooAQ9KWkyKWgApoGadSE4oAayBlIIBHoa47xF4ChvN09h+6m5Zo/wCFq7Sm/hQB4VdWk1jM0U8ZjkHBU1HXsmueHbXXINkqhZB92QDkV5brfh+60K4Mcy7oz92RehoAzaKRulBGaAFooooAKQDJpaKACiiigAooooAKKKKAE20N0o60tABRRRQAUUUUAFFFFACDqaKWigAooooAKKRelLQAUUUUAFFI3SloAKKKKACiiigAooooAKKKKACiiigAp8EElzKscal3Y4AFFvbyXUyRRIXkc4AFeo+FvCEWixrNMA92Rz6L9KAKvhPwWmnKt1eKHuTyE6hP/r12GBR2paAEwKWikAxQAtFFFABRRRQAUUUmBQAtFFFABSdRS0nUUALRRRQAUUUUAFFJ0FLQAUUg6UtABRRRQAUUUUAFFFFABRRRQAUUnQUtACYFVdQ0+DUrdoZ0Dowx9Kt0UAeR+JvCc+hSl0BktD0fuPY1z/8AFXut3bR3kDRSqHRhgg15h4s8IvormeAGS0bvjlD70AcyvSloooAKKKKACiiigAooooAKKTrS0AFI3SlooAKRulLRQAUUUUAFFFFAAOoooHUUUAFFFFABRRRQAUUUUAFFJ1paACiiigAooooAKKKKACiiigAzinxRPcSpFGMuxwB60wAsQByT0Fek+CfCa2EK3tyga6b7oP8AAP8AGgC14P8ACq6NB58yhrlx1x90e1dOBxQBxS0AFFFFABRRRQAUUUUAIelLRRQAUUUUAJ1FLRRQAmBS0UUAJgUtIelLQAUUUUAFJ3paKACik7UtABRRRQAUnUUtFACHpS0UnQUALSYFLRQAUUUUAFFFFABUU0KTxNHIodGGCpGQalooA8l8WeFX0OYyxjNqx4P92uer3O8tIr6B4ZkDxsMEGvJPE/h6TQL5lAJt2OY29vQ0AY9Jto3UtACbaWiigBF6UAYpaKACiiigAooooAKKKTdQAtFFFABRRRQADqKKB1ooAKT+KlooAKKKM5oAKKKKAE/hpQaT+KloAKKKKACiiigAooooAKKK2vCvh9tevwD8tvGcu39KANvwJ4XFy32+6TMY/wBUjD7x/vV6IuAMVHBCsESxxgKijAAHAqUdKAFooooAKKKKACiiigAooooAKKKKACk6ClooAKKKKACiiigAoopD0oAWikwKWgBMijIpaKACiikwKAFooooAKKTvS0AFFFFABRRRQAUUUUAFFFFABRRRQAh6VQ1fSYdYs3t5lBBHDd1PrWhSYFAHiGq6ZNpF9JbSjBU8E8bh61Ur1fxh4cXXLHfGALmIZU+vsa8okRo3ZWGGBwR6UAFFFJ/FQAtFFFABRSbaG6UALRRRQAHmiik20ALRRRQAUUUUAA60UDqKKACiiigApOlG6loAKQHNLRQAnvS0UUAFFFJuoAWiiigAooooAltbWS9uEhiUs7nAwK9j0HRo9E02O2Q5IGWb1Nc18PdAWO3/ALRlU+a/EYPYetduBigAAxS0UUAFIOlLRQAUUUUAFFFFABRRRQAUh6UtFABRRRQAUUUUAFJgUtMoAfRRRQAUUynYFAC0U3bTqACiiigAooooAKKKTtQAtFFFABRRRQAUUUUAFFN/GnUAFFFFABRRTKAF21558QfDvkS/2jApKucSADofWvRMCoLq1S8t5IZRuRxtYe1AHhlFaGv6S2i6nLbHOwHKE9xWfQAUUUUABGKKKKAE3UtFJ/DQAEZo/hpaKACiik/ioAWik/hpaAAHmikHVaKAFooooAT8KN1LRQAUUUUAFFFFABRRRQAUUUUAFanhvSG1nVYYcHywdznHassAngda9X8E6GNJ0tJHQC4mG5jjkDsKAN+KJYY1RFCKowABxUtFFABRRRQAUg6UtFABRRRQAnQUtFFACYFLRRQAUUUUAFFFFABRRRQAUUUUAFFFJ3oAWiiigAopOgpaACkPSlooAKKKKACik70tABRSfhS0AFFFFABSDpS0UAJgUtFFABRRRQAUUUUAFNAzS4FLQBy/jnRBqelNNGoM8PzDA5I7ivLK95KhgQQCD1Brybxron9kaszRrtgm+ZQBwPUUAc/SZ5paRelAC0UUjdKADpS0nSloAKKKKAEBzS0UUAJ/DS0nNHSgBR1ooHUUUAFFB60h6tQAtJ/FS0UAFFFFABRRRQAUUUUAFFFJ1oA3PB+jnV9YjDDMMXzuf6V68oC4A4A7VzPgLSxYaMsjIVkn+Y59O1dRQAUh6UtFABRRRQAUUUUAFFFFABRRRQAUUUUAIelLRRQAUh6UtFABRRRQAUUUUAFFFFABRSE4oyKAFooooAKKKKACiiigBOopaKKACik/GloAToKWiigAoopO9AC0gGKWigAopMiloAKKKTIoAWkPSlooAKwPGGjDWNIkCrmaP50reyKT+GgDwUjaSCORwaWt3xppY03XJdiFYpfnX+tYVACbqWk9aM80ALRRRQAUUnWloAT0oPUUtFABSD0o/hpaAAdaKB1FFACfjRtpaKAE20baF6Ue1AC0UUUAFFFFABRRRQAVoeHtP/tPVreDaWUtlsegrPr0D4a6WUinvnA+f5EyPzoA7eOMRKqKMKowBUlFFABRSdqWgAooooAKKKKACiiigAooooAKKKKACiiigAooooAKQ9KMiloAKKKKAE6CloooATPtS0UmRQAZFLRRQAUUyn0AFJ0FLRQAnej8KWigBD0paKKACiiigApMilooAQdKWiigAooooAKTIpaToKAFoopOooAWkPSlooA5Tx/pYvdI89VJlgO4EenevLyTXutxEJ4XjYAhhg5rxbWLB9M1Ge3cYKsccdu1AFOik7GloAKKKKACiiigAopCM0tABRRRQAg6rRQOpooAWkXpRkUtABRRSL0oAWkXpS0UAFFFFABRRRQA6NGldUUZZjgCvaNDsF0zTLeBV2lVGQPWvMPBunf2hrsCkApH85B9q9fAwOKADtS0UUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAIelLRRQAUnelpMCgA6ilopOgoAWiikwKAFooooAKKKTAoAMCloooAKKKKACiiigAooooAKKKKACiiigAooooATpmloooAKKKQDFAC0UUUAFIelLTKAH0UUUAJ2rzn4laWIbmC9RcLINjtnuOlej1heMdN/tHQp1ABdBvUn1FAHkC9KP4aWigApP4aWigApNtLSbqAFopN1LQAUUgOaWgBB1WigdVooAP4aN1LRQAi9KWiigAoooHNABRSbaWgAooooA9A+Gdjtgubs/xsEX8Otd3WL4TsjY6BaRn7xXecepraoAKKKKACiiigAopD0paACiikHSgA7UtJ2paACiiigAooooAKKKKACkwKWigApD0paKAEwKWiigAooooAKTApaKACiiigAooooATvTafRQAneloooAKKKKACiikPSgBtPoooAKKKKAEPSlpOopaACiiigApO1LSDpQAtJgUHpS0AFRyoJI2Q9GGKkprdaAPEdXtDYancwH+ByB9KqV1PxEsvs+sLNjCzL29a5agBP4qP4qWk60ALRRScUALRSbqWgBAcUfxUtJ1oAUdaKQYzRQAtFFFABRRSA+tAC0m2looATrS0UUAFWNOgN3f28IGd7har1veCLX7T4hgP8KZc5oA9YijWKNUAwFAAFS0UUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUh6UALRRRQAUUUUAFFFFABRRSYFAC0UUUAFFJkUtABRRRQAUUUUAFFFFABRRRQAUh6UDpS0AFFFJ2oAWik6iloAKKKKAE6iloplAD6QdKWigApvU0uRTaAOO+JVmJNMguAOY3wT7GvOMcV7B4vtPtfh68QfeVN4+orx+gAoopN1AC0UUUAFFFFACL0paKKAAdaKAeRRQAUUhOKWgBN1H9KWigAooooAKKKKAEbpXb/AAytA1zdXBydqhRXE16T8NbcJpM0u3Bd+tAHZUUg6UtABRRRQAUUUUAFFFFABRRRQAUUnaloAKKKKACiiigAooooAKKKQ9KAFooooAKKKTqKAFooooAKKKKAEPSlpB0paAEPSloooAKKKKACiim/w0AOooooAKKQ9KWgAooooAKKKKACiiigApO3rS0UAFFJ2paAEyKWiigApB0paKAIbmITW8kZ6MpBrw25h+zzyRHqjFefavdjyK8Y8SwC2128QLtXzCQPrQBm0UUUAI3SlopAc0ADdKWikzzQAtJ1o20tAAO1FJnHPpRQAtFFFABSfxUbaWgAooooATdS0UUAFes+BYBB4ct/ViWJryUnFezeG4jFoNiOuYlPT1FAGtRRRQAUUUUAFFFFABRRRQAUUUmRQAdBS0UUAFFJ3paACiiigAopD0paACiiigAooooATvS0UUAIelLSE4paACiik70ALRRRQAUUnajIoAMCloooAKKKTtQAtFFFABRRRQAnalpO9HagBaKKKACiiigBPwpaKKACiiigBMiloooAKKKToKAEbrXk/j2AReIpSOA6g16wegrzT4lx7NWt2zndH+XNAHI0UUUAFJ/FR/DS0AJ1o4paKACiiigBD3opaKAG5Wl3UUUAAOaBz6UUUAA6mloooAKQketFFACoRvHPevcNNUQ6fbIWGVjUfpRRQBZ3j1FG4eo/OiigA3j1FG8eooooAN49RRvHqKKKAFyPUUm8eooooAXI9RSbx6iiigA3D1H50uR6iiigBNw9R+dG4eo/OiigA3j1FG8eooooAXI9RRkeooooAMj1FJvHqKKKADePUUbgO9FFABuHqPzo3D1H50UUAG4Z6j86N49RRRQAbx6ijcPUfnRRQAbx6ijePUUUUAG8eoo3D1H50UUAG8eooDD1FFFABvHqKNw9R+dFFABuB7/rRvHqKKKADePUUbx6iiigA3D1H50bx6iiigA3D1H50bh6j86KKADePUUbx6iiigA3D1H50bh6j86KKADePUUbx6iiigA3D1H50bh6j86KKADcM9RRvHqKKKAAsMdR+def/E5Bvs3yDwRiiigDhdw9aWiigBMijiiigA3UtFFACbqOKKKAA8DtRRRQB//Z";
            byte[] image = Base64.getDecoder().decode(imageBase64);
            if (student.getPicture().length == 0) {
                student.setPicture(image);
            }
            studentService.addStudent(student);
        } else {
            Student st = studentService.findStudentByIdSetDiscipline(student.getId());
            Set<Discipline> disciplines = st.getDisciplines();
            if (disciplines.size() != 0) {
                student.setDisciplines(disciplines);
            }
            List<Average> averages = st.getAverages();
            if (averages.size() != 0) {
                student.setAverages(averages);
            }
            student.setLibraryAbonament(st.getLibraryAbonament());
            if (student.getPicture().length == 0) {
                student.setPicture(st.getPicture());
            }
            studentService.updateStudent(student);
        }
    }

    @GetMapping(value = "/DELETE")
    public ModelAndView deleteStudent(@RequestParam("check") String[] listId) {
        for (String studentId : listId) {
            studentService.deleteStudent(Long.parseLong(studentId));
        }
        return new ModelAndView("redirect:/");
    }

    @GetMapping(value = "/EMAIL")
//    public ModelAndView sendEmail(){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("EmailForm");
//        modelAndView.setStatus(HttpStatus.OK);
//        return modelAndView;
//    }
    public void sendEmail() {

        SendEmail.send( "borsveronica@gmail.com", "hello javatpoint", "How r u?");
    }

//    @WebServlet("/EmailSendingServlet ")
//    public class EmailSendingServlet extends HttpServlet {
//        private String host;
//        private String port;
//        private String user;
//        private String pass;
//
//        public void init() {
//            // reads SMTP server setting from web.xml file
//            ServletContext context = getServletContext();
//            host = context.getInitParameter("host");
//            port = context.getInitParameter("port");
//            user = context.getInitParameter("user");
//            pass = context.getInitParameter("pass");
//        }
//
//        protected void doPost(HttpServletRequest request,
//                              HttpServletResponse response) throws ServletException, IOException {
//            // reads form fields
//            String recipient = request.getParameter("recipient");
//            String subject = request.getParameter("subject");
//            String content = request.getParameter("content");
//
//            String resultMessage = "";
//
//            try {
//                EmailUtility.sendEmail(host, port, user, pass, recipient, subject,
//                        content);
//                resultMessage = "The e-mail was sent successfully";
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                resultMessage = "There were an error: " + ex.getMessage();
//            } finally {
//                request.setAttribute("Message", resultMessage);
//                getServletContext().getRequestDispatcher("/Result.jsp").forward(
//                        request, response);
//            }
//        }
//    }

    @GetMapping(value = "/LIBRARY_ABONAMENT")
    public ModelAndView editLibraryAbonament(@RequestParam(value = "id") Long id) {
        Student student = studentService.findStudentByIdSetDiscipline(id);
        LibraryAbonament libraryAbonament = student.getLibraryAbonament();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("student", student);
        modelAndView.addObject("libraryAbonament", libraryAbonament);
        modelAndView.setViewName("abonament");
        modelAndView.setStatus(HttpStatus.OK);
        return modelAndView;
    }

    @PostMapping(value = "/updateLibraryAbonament")
    public void saveLibraryAbonament(@Valid @ModelAttribute(value = "libraryAbonament") LibraryAbonament libraryAbonament, BindingResult bindingResult) {
        studentService.updateLibraryAbonament(libraryAbonament);
    }

    @GetMapping(value = "/MARK")
    public ModelAndView addMark(@RequestParam(value = "id") Long id) {
        Student student = studentService.findStudentByIdSetDiscipline(id);
        Mark mark = new Mark();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("student", student);
        modelAndView.addObject("mark", mark);
        modelAndView.setViewName("mark");
        modelAndView.setStatus(HttpStatus.OK);
        return modelAndView;
    }

    @PostMapping(value = "/ADD_MARK")
    public void addMark(@Valid @ModelAttribute(value = "mark") Mark mark, BindingResult bindingResult) {
        studentService.addMark(mark);
    }

    @GetMapping(value = "/DISCIPLINE")
    public ModelAndView addDisciplines(@RequestParam(value = "id") Long id) {
        Student student = studentService.findStudentByIdSetDiscipline(id);
        List<Discipline> disciplines = studentService.findAllDisciplines();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("student", student);
        modelAndView.addObject("disciplines", disciplines);
        modelAndView.setViewName("discipline");
        modelAndView.setStatus(HttpStatus.OK);
        return modelAndView;
    }

    @PostMapping(value = "/ADD_DISCIPLINE")
    public void addMark(@Valid @ModelAttribute(value = "discipline") List<Discipline> disciplines, BindingResult bindingResult) {
//        studentService.addMark(mark);
    }

    @GetMapping(value = "/pdf")
    public ModelAndView pdf() {
        return new ModelAndView("pdfDocument", "modelObject", studentsList);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());

    }

//    @InitBinder
//    public void initBind(WebDataBinder dataBinder) {
//
//        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
//
//        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
//    }
}
