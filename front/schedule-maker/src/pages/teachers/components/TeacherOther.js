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

export function CreateTeacher() {
  const [subjects, setSubjects] = useState([]);
  const [subject, setSubject] = useState("");
  const [chosenSubjects, setChosenSubjects] = useState([]);
  const [freeSubjects, setFreeSubjects] = useState([]);
  const [showSubjSelect, setShowSubjSelect] = useState(false); //show/hide
  
    const [fName, setFName] = useState("");
  const [lName, setLName] = useState("");

  const [shifts, setShifts] = useState([]);
  const [selectedShift, setSelectedShift] = useState("");

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

    if (fName === "" || errorFname.error) {
      setErrorMessage("Įveskite/pataisykite vardą !");
      isCorrect = false;
    }
    else if (lName === "" || errorLname.error) {
      setErrorMessage("Įveskite/pataisykite pavardę !");
      isCorrect = false;
    }
    else if (phoneNumber === "" || directEmail  === "" || teamsName  === "" || teamsEmail === "" ||
        errorPhoneNumber.error   || errorDirectMail.error    || errorTeamsName.error    || errorTeamsMail.error      ) {
          setErrorMessage("Įveskite/pataisykite kontaktinius duomenis !");
      isCorrect = false;
    }
    // else if (workHours === "" || errorHours.error) {
    //   // console.log(workHours + ", " + errorHours.error);
    //   setErrorMessage("Įveskite/pataisykite valandų skaičių !");
    //   isCorrect = false;
    // }
    else if (selectedShift === "" ) {
      setErrorMessage("Pasirinkite pamainą");
      isCorrect = false;
    }
    else{
      isCorrect = true;
    }
    
    isCorrect && createTeacher();

  }

  function fetchData() {
    getDataFrom("api/v1/teachers/subjects", (data) => {
      setSubjects(data.body);
      setFreeSubjects(data.body);
    });

    getDataFrom("api/v1/shift/get-active", (data) => {
      setShifts(data.body);
    });
  }

  useEffect(() => {
    fetchData();
  }, []);

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
    postDataTo(
      {
        body: body,
        contacts: teacherContacts,
        shift: teacherShiftDto,
        subjects: chosenSubjects,
      },
      (data) => {
        applyResult(data);
      }
    );
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
    const tempSubjects = freeSubjects.filter(
      (subA) =>
        !chosenSubjects.map((subC) => subC.subjectId).includes(subA.subjectId)
    );
    setSubjects(tempSubjects);
  }, [showSubjSelect]);

  const handleShowSubjects = () => {
    setShowSubjSelect(!showSubjSelect);
  };

  const handleAddChosen = (subjectNew) => {
    // setSubject(subjectNew);

    const temp = [...chosenSubjects, subjectNew];
    setChosenSubjects(temp);
    const removed = subjects.filter(
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

  const row1 = 3.5;
  const row2 = 3.5;
  const row3 = 3.5;
  return (
    <Container style={{ maxWidth: "75rem" }}>
      <form>
        <h3 className="create-header">Pridėti naują mokytoją</h3>

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
              id="fname"
              value={fName}
              onChange={(e) => {
                validate({
                  name: "fName",
                  symbolsToAllow: "",
                  maxLength: 30,
                  value: e.target.value,
                });
                errorMessage && setErrorMessage("");
                setFName(e.target.value);
              }}
            ></TextField>
          </Grid>

          {/* //33370 */}
          <Grid item sm={7}>
            
          </Grid>

          <Grid item sm={5}>
            <TextField
              error={errorLname.error}
              helperText={errorLname.text}
              fullWidth
              required
              variant="outlined"
              label="Pavardė"
              id="lname"
              value={lName}
              onChange={(e) => {
                validate({
                  name: "lName",
                  symbolsToAllow: "",
                  maxLength: 30,
                  value: e.target.value,
                });
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
              id="phoneNumber"
              value={phoneNumber}
              onChange={(e) => {
                setPhoneNumber(e.target.value);
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
              id="directEmail"
              value={directEmail}
              onChange={(e) => {
                setDirectEmail(e.target.value.toLowerCase());
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
              id="teamsName"
              value={teamsName}
              onChange={(e) => {
                validate({
                  name: "teamsName",
                  symbolsToAllow: "",
                  maxLength: 15,
                  value: e.target.value,
                });
                setTeamsName(e.target.value);
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
              id="teams_email"
              value={teamsEmail}
              onChange={(e) => {
                setTeamsEmail(e.target.value.toLowerCase());
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
              id="workHours"
              value={workHours}
              onBlur={(e) =>
                validate({
                  name: "workHours",
                  min: 0,
                  max: 80,
                  value: e.target.value,
                })
              }
              onChange={(e) => setWorkHours(e.target.value)}
            ></TextField>
          </Grid>
          <Grid item sm={7}></Grid>
          <Grid item sm={5}>
            <FormControl required fullWidth>
              <InputLabel id="shift-label">Galima pamaina</InputLabel>
              <Select
                label="Galima pamaina"
                labelId="shift-label"
                id="shift"
                // value={selectedShift.name}
                value={selectedShift === "" ? selectedShift : shifts[0]}
                onChange={(e) => setSelectedShift(e.target.value)}
              >
                {shifts.map((shift) => (
                  <MenuItem key={shift.id} value={shift}>
                    {" "}
                    {shift.name}{" "}
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
                  value=""
                  defaultOpen={true}
                  onChange={(e) => handleAddChosen(e.target.value)}
                >
                  {freeSubjects.map((subject, index) => (
                    <MenuItem key={index} value={subject}>
                      {subject.name}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            )}

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
                  {chosenSubjects.map((subject, index) => (
                    <TableRow key={index}>
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
                Sukurti
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
