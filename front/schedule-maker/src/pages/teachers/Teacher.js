import {
  Alert,
  Button,
  Container,
  FormControl,
  InputLabel,
  Grid,
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
import { getDataFrom, postDataTo } from "./components/Fetch";
import ".././pages.css";
import "../teachers/Teacher.css";

export function Teacher({ mode, teacherId, onSave }) {
  const [subjects, setSubjects] = useState([]);
  const [subject, setSubject] = useState("");
  const [chosenSubjects, setChosenSubjects] = useState([]);
  const [freeSubjects, setFreeSubjects] = useState([]);
  const [showSubjSelect, setShowSubjSelect] = useState(false); //show/hide

  const [entity, setEntity] = useState({});
  const [teacher, setTeacher] = useState({});

  const [fName, setFName] = useState("");
  const [lName, setLName] = useState("");

  const [phoneNumber, setPhoneNumber] = useState("");
  const [directEmail, setDirectEmail] = useState("");
  const [teamsName, setTeamsName] = useState("");
  const [teamsEmail, setTeamsEmail] = useState("");
  const [contacts, setContacts] = useState("");

  const [workHours, setWorkHours] = useState(0);

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
  // const [errorShift, setErrorShift] = useState(errorObject);
  const [shifts, setShifts] = useState([]);
  const [selectedShift, setSelectedShift] = useState("");

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

    // if (fName === "" || errorFname.error) {
    if (teacher.fName === "" || errorFname.error) {
      setErrorMessage("Įveskite/pataisykite vardą !");
      isCorrect = false;
    } else if (teacher.lName === "" || errorLname.error) {
      setErrorMessage("Įveskite/pataisykite pavardę !");
      isCorrect = false;
    } else if (
      teacher.contacts.phoneNumber === "" ||
      teacher.contacts.directEmail === "" ||
      teacher.contacts.teamsName === "" ||
      teacher.contacts.teamsEmail === "" ||
      errorPhoneNumber.error ||
      errorDirectMail.error ||
      errorTeamsName.error ||
      errorTeamsMail.error
    ) {
      setErrorMessage("Įveskite/pataisykite kontaktinius duomenis !");
      isCorrect = false;
    } else if (teacher.workHours === "" || errorHours.error) {
      // console.log(workHours + ", " + errorHours.error);
      setErrorMessage("Įveskite/pataisykite valandų skaičių !");
      isCorrect = false;
    } else if (teacher.selectedShift === "") {
      setErrorMessage("Pasirinkite pamainą");
      isCorrect = false;
    } else {
      isCorrect = true;
    }

    // isCorrect && createTeacher();
    onSave(teacher);
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
      console.log(data.body);

      setChosenSubjects(data.body.subjectsList);
      setWorkHours(data.body.workHoursPerWeek);
      setSelectedShift(data.body.teacherShiftDto);
    });
  }

  function handleChange(event) {
    const { id, value } = event.target;
    console.log(teacher);
    console.log(id + " " + value);

    if (id.startsWith("contacts.")) {
      const contactsProp = id.split(".")[1];

      setTeacher((prevTeacher) => ({
        ...prevTeacher,
        contacts: { ...prevTeacher.contacts, [contactsProp]: value },
      }));
    } else if (id === "workHoursPerWeek") {
      setTeacher((prevTeacher) => ({ ...prevTeacher, [id]: Number(value) }));
    } else if (id === "subjectsList") {
      setTeacher((prevTeacher) => ({ ...prevTeacher, [id]: value }));
    } else {
      setTeacher((prevTeacher) => ({ ...prevTeacher, [id]: value }));
    }
    console.log(id + " " + value);
    console.log(teacher);
  }

  function handleSubmit(event) {
    event.preventDefault();
    onSave(entity);
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

  const createTeacher = () => {
    const body = {
      id: 0,
      fName: fName,
      lName: lName,
      active: true,
      workHoursPerWeek: workHours,
    };
    const teacherContacts = {
      phoneNumber,
      directEmail,
      teamsEmail,
      teamsName,
    };

    const teacherShiftDto = {
      id: selectedShift.id,
      name: selectedShift.name,
    };
    setTeacher((prevTeacher) => ({
      ...prevTeacher,
      workHoursPerWeek: workHoursPerWeek,
    }));
    setTeacher((prevTeacher) => ({
      ...prevTeacher,
      subjectsList: chosenSubjects,
    }));
    onSave(teacher);

    // postDataTo(
    //   {
    //     body: body,
    //     contacts: teacherContacts,
    //     shift: teacherShiftDto,
    //     subjects: chosenSubjects,
    //   },
    //   (data) => {
    //     applyResult(data);
    //   }
    // );
  };

  const applyResult = (result) => {
    if (result.status >= 300) {
      setCreateMessage("");
      setErrorMessage(result.body.message);
    } else {
      setCreateMessage("Sukurta sekmingai!");
      setErrorMessage("");
      clear();
    }
  };

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

    setSelectedShift("");
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
    setShowSubjSelect(false);
  };
  function showInfo() {
    console.log(teacher);
    console.log(freeSubjects);
    console.log(chosenSubjects);
  }

  return (
    <Container style={{ maxWidth: "75rem" }}>
      <form>
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
                validate({
                  name: "fName",
                  symbolsToAllow: "",
                  maxLength: 30,
                  value: e.target.value,
                });
                errorMessage && setErrorMessage("");
                setFName(e.target.value);
                // handleChange(e);
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
              id={lName}
              // value={teacher.lName || ""}
              value={lName || ""}
              onChange={(e) => {
                validate({
                  name: "lName",
                  symbolsToAllow: "",
                  maxLength: 30,
                  value: e.target.value,
                });
                // handleChange(e);
                setLName(e.target.value);
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
              value={
                (teacher.contacts ? teacher.contacts.phoneNumber : "") || ""
              }
              onChange={(e) => {
                setPhoneNumber(e.target.value);
                // handleChange(e);
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
              value={teacher.contacts ? teacher.contacts.directEmail : "" || ""}
              onChange={(e) => {
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
              value={teacher.contacts ? teacher.contacts.teamsName : "" || ""}
              onChange={(e) => {
                validate({
                  name: "teamsName",
                  symbolsToAllow: "",
                  maxLength: 15,
                  value: e.target.value,
                });
                handleChange(e);
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
              value={teacher.contacts ? teacher.contacts.teamsEmail : "" || ""}
              onChange={(e) => {
                handleChange(e);
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
              // value={teacher.workHoursPerWeek || 0 }
              value={workHours || 0}
              //nepasisetina
              onBlur={(e) =>
                validate({
                  name: "workHours",
                  min: 0,
                  max: 80,
                  value: e.target.value,
                })
              }
              onChange={(e) => {
                // handleChange(e);
                setWorkHours(Number(e.target.value));
              }}
            ></TextField>
          </Grid>
          <Grid item sm={7}></Grid>
          <Grid item sm={5}>
            <FormControl required fullWidth>
              <InputLabel id="shift-label">Galima pamaina</InputLabel>
              <Select
                label="Galima pamaina"
                labelId="shift-label"
                id="selectedShift"
                // value={selectedShift.name}
                // value={teacher.teacherShiftDto.name || shifts[0]}
                value={selectedShift.id || ""}
                onChange={(e) => handleChange(e)}
              >
                {shifts.map((shift) => (
                  <MenuItem key={shift.id} value={shift.id}>
                    {shift.name}
                  </MenuItem>
                ))}
              </Select>
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
                  onChange={(e) => handleAddChosen(e.target.value)}
                >
                  {freeSubjects.map((subject) => (
                    <MenuItem key={subject.id} value={subject}>
                      {subject.name}
                    </MenuItem>
                  ))}
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
                  <TableRow >
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
                 
                  {chosenSubjects.map((subject) => (
                    <TableRow key={subject.id}>
                      
                      <TableCell align="left" component="th" scope="row">
                       {subject.name}
                      </TableCell>
                      
                      <TableCell align="center">
                        {subject.module ? ( subject.module ) : ( <p>Nenurodytas</p>)}
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
