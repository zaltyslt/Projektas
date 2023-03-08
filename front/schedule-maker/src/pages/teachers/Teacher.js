import {
  Alert,
  Button,
  Container,
  FormControl,
  InputLabel,
  Grid,
  FormHelperText,
  MenuItem,
  Paper,
  Select,
  Stack,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TextField,
} from "@mui/material";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import { width } from "@mui/system";
import {
  validateText,
  validateEmail,
  validatePhone,
  validateNumber,
} from "./components/Validations";
import { getDataFrom } from "./components/Fetch";
import ".././pages.css";
import "../teachers/Teacher.css";

export function Teacher({ mode, teacherId, onSave, handleSave }) {
  const [subjects, setSubjects] = useState([]);
  const [subject, setSubject] = useState("");
  const [chosenSubjects, setChosenSubjects] = useState([]);
  const [freeSubjects, setFreeSubjects] = useState([]);
  const [showSubjSelect, setShowSubjSelect] = useState(false); //show/hide

  const [teacher, setTeacher] = useState({});

  const [id, setid] = useState("");
  const [fName, setFName] = useState("");
  const [lName, setLName] = useState("");

  const [phoneNumber, setPhoneNumber] = useState("");
  const [directEmail, setDirectEmail] = useState("");
  const [teamsName, setTeamsName] = useState("");
  const [teamsEmail, setTeamsEmail] = useState("");
  const [contacts, setContacts] = useState("");

  const [workHours, setWorkHours] = useState(0);
  const [shifts, setShifts] = useState([]);
  const [shift, setShift] = useState("");

  const [errorMessage, setErrorMessage] = useState("");
  const [createMessage, setCreateMessage] = useState("");

  const errorObject = { state: false };
  const [errorFname, setErrorFname] = useState(errorObject);
  const [errorLname, setErrorLname] = useState(errorObject);
  const [errorPhoneNumber, setErrorPhoneNumber] = useState(errorObject);
  const [errorDirectMail, setErrorDirectMail] = useState(errorObject);
  const [errorTeamsName, setErrorTeamsName] = useState(errorObject);
  const [errorTeamsMail, setErrorTeamsMail] = useState(errorObject);
  const [errorHours, setErrorHours] = useState(errorObject);
  const [errorShift, setErrorShift] = useState(errorObject);

  let navigate = useNavigate();

  function validate(field) {
    field.name === "fName" && setErrorFname(validateText(field));
    field.name === "lName" && setErrorLname(validateText(field));
    field.name === "phoneNumber" && setErrorPhoneNumber(validatePhone(field));
    field.name === "directEmail" && setErrorDirectMail(validateEmail(field));
    field.name === "teamsName" && setErrorTeamsName(validateText(field));
    field.name === "teamsEmail" && setErrorTeamsMail(validateEmail(field));
    field.name === "workHours" && setErrorHours(validateNumber(field));
  }

  function preCreateCheck() {
    let isCorrect = true;
    //  console.log("boom");
    
    if (!teacher.fName || teacher.fName === "" || errorFname.error) {
      setErrorMessage("Įveskite/pataisykite vardą !");
      isCorrect = false;
    } else if (!teacher.lName || teacher.lName === "" || errorLname.error) {
      setErrorMessage("Įveskite/pataisykite pavardę !");
      isCorrect = false;
    } else if (
      !teacher.contacts.phoneNumber  || teacher.contacts.phoneNumber === "" ||
      !teacher.contacts.directEmail || teacher.contacts.directEmail === "" ||
      !teacher.contacts.teamsName || teacher.contacts.teamsName === "" ||
      !teacher.contacts.teamsEmail || teacher.contacts.teamsEmail === "" ||
      errorPhoneNumber.error ||
      errorDirectMail.error ||
      errorTeamsName.error ||
      errorTeamsMail.error
    ) {
      setErrorMessage("Įveskite/pataisykite kontaktinius duomenis !");
      isCorrect = false;
    } else if ( teacher.workHoursPerWeek === "" || errorHours.error) {
      // console.log(workHours + ", " + errorHours.error);
      setErrorMessage("Įveskite/pataisykite valandų skaičių !");
      isCorrect = false;
    } else if (!teacher.selectedShift.id ||teacher.selectedShift.id === "") {
      setErrorMessage("Pasirinkite pamainą");
      isCorrect = false;
    } else {
      isCorrect = true;
    }

    isCorrect && createTeacher();
    // onSave(teacher);
  }

  // function fetchData() {
  //   getDataFrom("api/v1/teachers/subjects", (data) => {
  //     setSubjects(data.body);
  //     setFreeSubjects(data.body);
  //   });

  //   getDataFrom("api/v1/shift/get-active", (data) => {
  //     setShifts(data.body);
  //   });
  // }

  // useEffect(() => {
  //   fetchData();
  // }, []);

  useEffect(() => {
    fetchData();

    if (mode === "update") {
      fetchTeacherData();
    }
  }, []);

  async function fetchData() {
    getDataFrom("api/v1/teachers/subjects", (data) => {
      setSubjects(data.body);
      setFreeSubjects(data.body);
    });

    getDataFrom("api/v1/shift/get-active", (data) => {
      setShifts(data.body);
    });
  }

  async function fetchTeacherData() {
    getDataFrom("api/v1/teachers/view?tid=" + teacherId, (data) => {
      setTeacher(data.body);
      // console.log(data.body);      //33370
      setid(data.body.id);
      setFName(data.body.fName);
      setLName(data.body.lName);
      setWorkHours(data.body.workHoursPerWeek);

      setPhoneNumber(data.body.contacts.phoneNumber);
      setDirectEmail(data.body.contacts.directEmail);
      setTeamsName(data.body.contacts.teamsName);
      setTeamsEmail(data.body.contacts.teamsEmail);

      setShift(data.body.selectedShift);
      // handleShift(data.body.teacherShiftDto);
      setChosenSubjects(data.body.subjectsList);
    });
  }
  function handleShift(teacherShift) {
    const shift = shifts.find(shift.id === teacherShift.id);
    console.log(teacherShift + ", " + shift);
  }

  useEffect(() => {
    const timer = setTimeout(() => {
      setCreateMessage("");
      setErrorMessage("");
    }, 5000);

    return () => {
      clearTimeout(timer);
    };
  }, [createMessage, errorMessage]);

  function handleChange(event) {
    // console.log(event);
    const { id, value } = event.target;
    console.log(id + " * " + value);
    const prefix = id.split(".");
    if (prefix[0] === "contacts" ) {
      setTeacher((prevTeacher) => ({ ...prevTeacher, contacts: {...prevTeacher.contacts, [prefix[1]]: value,}, }));
    } else {
      setTeacher((prevTeacher) => ({ ...prevTeacher, [id]: value }));
    }
  }

  const createTeacher = () => {
    // // setTeacher({

    // const teacher1 = {
    //   id: id,
    //   fName: fName,
    //   lName: lName,
    //   active: true,
    //   workHoursPerWeek: workHours,

    //   contacts: {
    //     phoneNumber,
    //     directEmail,
    //     teamsEmail,
    //     teamsName,
    //   },

    //   selectedShift: shift,
    //   subjectsList: chosenSubjects,
    // };
    // setTeacher(teacher1);

    async function teacherModifier(teacher) {
      const fetchResult = await onSave(teacher);
      applyResult(fetchResult);
    }

    teacherModifier(teacher);
  };

  async function deleteTeacher() {
    // console.log(teacher.id);
    onSave(teacher.id);
    navigate(-1);
  }

  function applyResult(result) {
    // console.log(result);
    if (result.status >= 300) {
      setCreateMessage("");
      console.log(result.body);
      setErrorMessage(result.body.message);
    } else {
      if(mode === "update"){
        setCreateMessage("Duomenys atnaujinti sėkmingai.");
        // window.location.assign("/teachers/view&tid="+teacher.id);
        localStorage.setItem('messageUpdateTeacher', 'Duomenys atnaujinti.');
        console.log(localStorage.getItem("messageUpdateTeacher"))
        navigate(-1);
      }else{
        setCreateMessage("Mokytojas sukurtas");
        clear();
      }
      setErrorMessage("");
  }
  }
  function clearMessages() {
    setCreateMessage("");
    setErrorMessage("");
  }

  const clear = () => {
    setFName("");
    setLName("");

    setPhoneNumber("");
    setDirectEmail("");
    setTeamsEmail("");
    setTeamsName("");

    setShift("");
    setWorkHours("");
    setSubjects("");
  };

  const isActive = [
    { value: "true", label: "Aktyvus" },
    { value: "false", label: "Neaktyvus" },
  ];

  useEffect(() => {
    const tempSubjects = subjects.filter(
      (subA) =>
        !chosenSubjects.map((subC) => subC.subjectId).includes(subA.subjectId)
    );
    setFreeSubjects(tempSubjects);
  }, [showSubjSelect]);

  const handleShowSubjects = () => {
    setShowSubjSelect(!showSubjSelect);
  };

  const handleAddChosen = (subjectNew) => {
    //  setSubject(subjectNew);

    const temp = [...chosenSubjects, subjectNew];
    setChosenSubjects(temp);
    handleChange({target:{id:'subjectsList', value: temp,}});
    //const evenNumbers = numbers.filter((number) => number % 2 === 0);
    const removed = freeSubjects.filter(
      (subject) => subject.subjectId != subjectNew.subjectId
    );
    setFreeSubjects(removed);
    setShowSubjSelect(false);
  };

  const handleRemoveChosen = (subjectRem) => {
    //patikrinti ar subjects neturi tokio dalyko
    console.log(freeSubjects);
    const moved = freeSubjects.filter(
      (subject) => subject.subjectId != subjectRem.subjectId
    );

    const removed = chosenSubjects.filter(
      (subject) => subject.subjectId != subjectRem.subjectId
    );
    setFreeSubjects([...moved, subjectRem]);
    setChosenSubjects(removed);
    handleChange({target:{id:'subjectsList', value: removed,}});
    setShowSubjSelect(false);
  };

  const handleShiftChange = (shiftId) => {
    const tempShift = shifts.find((shift) => shift.id === shiftId);
    console.log(tempShift);
    handleChange({target:{id:'selectedShift', value: tempShift}});
    handleChange({target:{id: 'active',        value: true}});

    setShift(tempShift);
  };

  function showInfo() {
    console.log(teacher);
    // console.log(freeSubjects);
    // console.log(chosenSubjects);
    // console.log(freeSubjects);
    // console.log(subject);
  }

  return (
    <Container style={{ maxWidth: "75rem" }}>
      <form>
      <Grid item sm={7}>
            {errorMessage && <Alert severity="warning">{errorMessage}</Alert>}
            {createMessage && <Alert severity="success">{createMessage}</Alert>}
          </Grid>
        <h3 className="create-header">
          {" "}
          {mode === "update"
            ? "Redaguoti mokytojo duomenis"
            : "Pridėti mokytoją"}
        </h3>

        <Grid
          container
          direction="row"
          justifyContent="space-between"
          alignItems="center"
          rowSpacing={3}
        >
          <Grid item sm={5}>
            <TextField
              error={errorFname.error}
              helperText={errorFname.text}
              fullWidth
              required
              variant="outlined"
              label="Vardas"
              id="fName"
              value={teacher.fName || ""}
              onChange={(e) => {
                handleChange(e);
                validate({
                  name: "fName",
                  symbolsToAllow: "",
                  maxLength: 30,
                  value: e.target.value,
                });
                errorMessage && setErrorMessage("");
                // setTeacher(e.target.value);
              }}
            ></TextField>
          </Grid>

          {/* //33370 */}
          <Grid item sm={7}>
            <Button variant="contained" onClick={() => showInfo()}>
              Rodyt
            </Button>
          </Grid>

          <Grid item sm={5}>
            <TextField
              error={errorLname.error}
              helperText={errorLname.text}
              fullWidth
              required
              variant="outlined"
              label="Pavardė"
              id="lName"
              value={teacher.lName || ""}
              onChange={(e) => {
                validate({
                  name: "lName",
                  symbolsToAllow: "",
                  maxLength: 30,
                  value: e.target.value,
                });
                handleChange(e);
                // setLName(e.target.value);
              }}
            ></TextField>
          </Grid>
          <Grid item sm={7}></Grid>

          <Grid item sm={5}>
            <TextField
              error={errorPhoneNumber.error}
              helperText={errorPhoneNumber.text}
              fullWidth
              required
              variant="outlined"
              label="Kontaktinis telefonas"
              id="contacts.phoneNumber"
              // value={teacher.contacts ? teacher.contacts.phoneNumber : "" || ""}
              value={teacher.contacts && teacher.contacts.phoneNumber || ""}
              onChange={(e) => {
                // setPhoneNumber(e.target.value);
                handleChange(e);
              }}
              onBlur={(e) => {
                validate({
                  name: "phoneNumber",
                  symbolsToAllow: "",
                  maxLength: 0,
                  value: e.target.value,
                });
              }}
            ></TextField>
          </Grid>
          <Grid item sm={7}></Grid>

          <Grid item sm={5}>
            <TextField
              error={errorDirectMail.error}
              helperText={errorDirectMail.text}
              fullWidth
              required
              variant="outlined"
              label="El. paštas"
              id="contacts.directEmail"
              // value={teacher.contacts ? teacher.contacts.directEmail : ""  || ""}
              value={teacher.contacts && teacher.contacts.directEmail|| ""}
              onChange={(e) => {
                // setDirectEmail(e.target.value);
                handleChange(e);
              }}
              onBlur={(e) => {
                validate({
                  name: "directEmail",
                  symbolsToAllow: "",
                  maxLength: 0,
                  value: e.target.value,
                });
              }}
            ></TextField>
          </Grid>
          <Grid item sm={7}></Grid>
          <Grid item sm={5}>
            <TextField
              error={errorTeamsName.error}
              helperText={errorTeamsName.text}
              fullWidth
              required
              variant="outlined"
              label="Teams vardas"
              id="contacts.teamsName"
              value={teacher.contacts && teacher.contacts.teamsName   || ""}
              onChange={(e) => {
                validate({
                  name: "teamsName",
                  symbolsToAllow: "",
                  maxLength: 15,
                  value: e.target.value,
                });
                // setTeamsName(e.target.value);
                handleChange(e)
              }}
            ></TextField>
          </Grid>
          <Grid item sm={7}></Grid>
          <Grid item sm={5}>
            <TextField
              error={errorTeamsMail.error}
              helperText={errorTeamsMail.text}
              fullWidth
              required
              variant="outlined"
              label="Teams el. paštas"
              id="contacts.teamsEmail"
              value={teacher.contacts && teacher.contacts.teamsEmail    || ""}
              onChange={(e) => {
                // setTeamsEmail(e.target.value);
                handleChange(e)
              }}
              onBlur={(e) =>
                validate({
                  name: "teamsEmail",
                  symbolsToAllow: "",
                  maxLength: 0,
                  value: e.target.value,
                })
              }
            ></TextField>
          </Grid>
          <Grid item sm={7}></Grid>
          <Grid item sm={5}>
            <TextField
              error={errorHours.error}
              helperText={errorHours.text}
              fullWidth
              required
              variant="outlined"
              label="Valandos per savaitę"
              id="workHoursPerWeek"
              value={teacher.workHoursPerWeek || 0}
              onBlur={(e) =>
                validate({
                  name: "workHours",
                  min: 0,
                  max: 80,
                  value: e.target.value,
                })
              }
              onChange={(e) => {
                // setWorkHours(e.target.value);
                handleChange(e);
              }}
            ></TextField>
          </Grid>
          <Grid item sm={7}></Grid>
          <Grid item sm={5}>
            <FormControl required fullWidth>
              <InputLabel id="shift-label">Pamaina</InputLabel>
              <Select
                required
                fullWidth
                label="Galima pamaina"
                labelId="shift-label"
                id="teacher.selectedShift"
                value={teacher.selectedShift && teacher.selectedShift.id  || ""}
                onChange={(e) => {
                  handleShiftChange(e.target.value);
                  // handleChange({id:"teacher.selectedShift",value:e.target.value});
                  // console.log(e.target.value);
                }}
              >
                {shifts.map((shift) => (
                  <MenuItem key={shift.id} value={shift.id}>
                    {shift.name}
                  </MenuItem>
                ))}
              </Select>
              {/* <FormHelperText>{shift.name}</FormHelperText> */}
            </FormControl>
          </Grid>
          <Grid item sm={7}></Grid>

          <Grid item sm={12}>
            {showSubjSelect && (
              <FormControl fullWidth>
                <InputLabel id="subjects-label">Dalykai</InputLabel>
                <Select
                  label="Dalyko pavadinimas"
                  labelId="subject-label"
                  id="subject"
                  value={subject}
                  defaultOpen={true}
                  onChange={(e) =>{ handleAddChosen(e.target.value);
                  }}
                >
                  {freeSubjects.map(
                    (
                      subject //map <======
                    ) => (
                      <MenuItem
                        key={"1131" + subject.subjectId}
                        value={subject}
                      >
                        {subject.name}
                      </MenuItem>
                    )
                  )}
                </Select>
              </FormControl>
            )}
          </Grid>

          <Grid item sm={12}>
            <TableContainer component={Paper} style={{ width: "100%" }}>
              <Table
                style={{ tableLayout: "fixed" }}
                aria-label="custom pagination table"
              >
                <TableHead>
                  <TableRow>
                    <TableCell align="left">Dėstomi dalykai</TableCell>
                    <TableCell align="center">Moduliai </TableCell>
                    <TableCell align="right">
                      <Button variant="contained" onClick={handleShowSubjects}>
                        Pridėti
                      </Button>
                    </TableCell>
                  </TableRow>
                </TableHead>

                <TableBody>
                  {chosenSubjects &&
                    chosenSubjects.map((subject) => (
                      <TableRow key={"1141" + subject.subjectId}>
                        <TableCell align="left" component="th" scope="row">
                          {subject.name}
                        </TableCell>

                        <TableCell align="center">
                          {subject.module ? subject.module : <p>Nenurodytas</p>}
                        </TableCell>

                        <TableCell align="right" className="activity">
                          <Button
                            variant="contained"
                            onClick={(e) => handleRemoveChosen(subject)}
                          >
                            Ištrinti
                          </Button>
                        </TableCell>
                      </TableRow>
                    ))}
                </TableBody>
              </Table>
            </TableContainer>
          </Grid>

          <Grid item sm={7}>
            {errorMessage && <Alert severity="warning">{errorMessage}</Alert>}
            {createMessage && <Alert severity="success">{createMessage}</Alert>}
          </Grid>
          <Grid item sm={12}>
            <Stack direction="row" spacing={2}>
              <Button variant="contained" onClick={preCreateCheck}>
                {mode === "update" ? "Išsaugoti" : "Sukurti"}
              </Button>
              {mode === "update" && (
                <Button variant="contained" onClick={deleteTeacher}>
                  Ištrinti
                </Button>
              )}

              <Button variant="contained" onClick={() => navigate(-1)}>
                Grįžti
              </Button>
            </Stack>
          </Grid>
        </Grid>
      </form>
    </Container>
  );
}
