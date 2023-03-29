import {
  Alert,
  Button,
  Container,
  FormControl,
  InputLabel,
  Grid,
  MenuItem,
  FormHelperText,
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
  Typography,
} from "@mui/material";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Link, useHref } from "react-router-dom";
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
  const [subject, setSubject] = useState([]);
  const [chosenSubjects, setChosenSubjects] = useState([]);
  const [freeSubjects, setFreeSubjects] = useState([]);
  const [showSubjSelect, setShowSubjSelect] = useState(false);
  const [teacher, setTeacher] = useState({});
  const [currentTacherName, setCurrentTeacherName] = useState("");
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
  const [errorSubjectsList, setErrorSubjectsList] = useState(errorObject);

  let navigate = useNavigate();
  const listUrl = useHref("/teachers");

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
    if (!teacher.fName || teacher.fName === "") {
      setErrorFname({ error: true, text: "Mokytojo vardas privalomas." });
      isCorrect = false;
    }
    if (!teacher.lName || teacher.lName === "") {
      setErrorLname({ error: true, text: "Mokytojo pavardė privaloma." });
      isCorrect = false;
    }
    if (
      !teacher.contacts ||
      !teacher.contacts.phoneNumber ||
      teacher.contacts.phoneNumber === ""
    ) {
      setErrorPhoneNumber({
        error: true,
        text: "Kontaktinis telefonas privalomas.",
      });
      isCorrect = false;
    }
    if (
      !teacher.contacts ||
      !teacher.contacts.directEmail ||
      teacher.contacts.directEmail === ""
    ) {
      setErrorDirectMail({
        error: true,
        text: "Elektroninio pašto adresas privalomas.",
      });
      isCorrect = false;
    }
    if (
      !teacher.selectedShift ||
      !teacher.selectedShift.id ||
      teacher.selectedShift.id === ""
    ) {
      setErrorShift({ error: true, text: "Pasirinkite pamainą." });
      isCorrect = false;
    }
    if (!teacher.subjectsList || teacher.subjectsList.length < 1) {
      setErrorSubjectsList({
        error: true,
        text: "Dėstomus dalykus pasirinkti privaloma",
      });
      isCorrect = false;
    }
    isCorrect && createTeacher();
  }

  useEffect(() => {
    fetchData();
    if (mode === "update") {
      fetchTeacherData();
    }
  }, []);

  async function fetchData() {
    getDataFrom("api/v1/teachers/subjects", (data) => {
      if (data) {
        setSubjects(data);
        setFreeSubjects(data);
      } else {
        setSubjects([]);
        setFreeSubjects([]);
        setErrorMessage("Nepavyko parsiųsti duomenų iš serverio. (Subject)");
      }
    });
    getDataFrom("api/v1/shift/get-active", (data) => {
      if (data) {
        setShifts(data);
      } else {
        setErrorMessage("Nepavyko parsiųsti duomenų iš serverio. (Pamaina)");
        setShifts([]);
      }
    });
  }

  async function fetchTeacherData() {
    getDataFrom("api/v1/teachers/view?tid=" + teacherId, (data) => {
      if (data) {
        setTeacher(data);
        setShift(data.selectedShift);
        setChosenSubjects(data.subjectsList);
        setCurrentTeacherName(data.fName + " " + data.lName);
      } else {
        setTeacher({});
        setErrorMessage("Nepavyko parsiųsti duomenų iš serverio. (Mokytojas)");
      }
    });
  }

  function clearErrorMessages() {
    setCreateMessage("");
    setErrorMessage("");
  }

  function handleChange(event) {
    clearErrorMessages();
    const { id, value } = event.target;
    const prefix = id.split(".");
    if (prefix[0] === "contacts") {
      setTeacher((prevTeacher) => ({
        ...prevTeacher,
        contacts: { ...prevTeacher.contacts, [prefix[1]]: value },
      }));
    } else {
      setTeacher((prevTeacher) => ({ ...prevTeacher, [id]: value }));
    }
  }

  const createTeacher = () => {
    async function teacherModifier(teacher) {
      const fetchResult = await onSave(teacher);
      applyResult(await fetchResult.json());
    }
    teacherModifier(teacher);
  };

  function deleteTeacher() {
    onSave(teacher.id);
    window.location = listUrl;
  }

  function applyResult(result) {
    if (result.status > 299) {
      setCreateMessage("");
      setErrorMessage(result.message);
    } else {
      if (mode === "update") {
        setCreateMessage("Mokytojo duomenys sėkmingai atnaujinti.");
        setCurrentTeacherName(teacher.fName + " " + teacher.lName);
      } else {
        setCreateMessage("Mokytojas sėkmingai sukurtas");
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
    setTeacher({});
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
    const temp = [...chosenSubjects, subjectNew];
    setChosenSubjects(temp);
    handleChange({ target: { id: "subjectsList", value: temp } });
    const removed = freeSubjects.filter(
      (subject) => subject.subjectId != subjectNew.subjectId
    );
    setFreeSubjects(removed);
    setShowSubjSelect(false);
    setErrorSubjectsList({ status: false });
  };

  const handleRemoveChosen = (subjectRem) => {
    console.log(freeSubjects);
    const moved = freeSubjects.filter(
      (subject) => subject.subjectId != subjectRem.subjectId
    );
    const removed = chosenSubjects.filter(
      (subject) => subject.subjectId != subjectRem.subjectId
    );
    setFreeSubjects([...moved, subjectRem]);
    setChosenSubjects(removed);
    handleChange({ target: { id: "subjectsList", value: removed } });
    setShowSubjSelect(false);
  };

  const handleShiftChange = (shiftId) => {
    const tempShift = shifts.find((shift) => shift.id === shiftId);
    handleChange({ target: { id: "selectedShift", value: tempShift } });
    handleChange({ target: { id: "active", value: true } });
    setErrorShift({ error: false });
    setShift(tempShift);
  };

  return (
    <Container>
      <form>
        <h3 className="create-header">
          {mode === "update" ? "Redagavimas" : "Pridėti naują mokytoją"}
        </h3>
        {mode === "update" && (
          <Grid>
            <h3>{currentTacherName}</h3>
            <p>Paskutinį kartą redaguota: {teacher && teacher.dateModified}</p>
          </Grid>
        )}
        <Grid container rowSpacing={2}>
          <Grid item sm={8}>
            {errorMessage && <Alert severity="warning">{errorMessage}</Alert>}
            {createMessage && <Alert severity="success">{createMessage}</Alert>}
          </Grid>
          <Grid item sm={8}>
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
              }}
            ></TextField>
          </Grid>
          <Grid item sm={8}>
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
              }}
            ></TextField>
          </Grid>
          <Grid item sm={8}>
            <TextField
              error={errorPhoneNumber.error}
              helperText={errorPhoneNumber.text}
              fullWidth
              required
              variant="outlined"
              label="Kontaktinis telefonas"
              id="contacts.phoneNumber"
              value={(teacher.contacts && teacher.contacts.phoneNumber) || ""}
              onChange={(e) => {
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
          <Grid item sm={8}>
            <TextField
              error={errorDirectMail.error}
              helperText={errorDirectMail.text}
              fullWidth
              required
              variant="outlined"
              label="El. paštas"
              id="contacts.directEmail"
              value={(teacher.contacts && teacher.contacts.directEmail) || ""}
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
          <Grid item sm={8}>
            <TextField
              error={errorTeamsName.error}
              helperText={errorTeamsName.text}
              fullWidth
              variant="outlined"
              label="Teams vardas"
              id="contacts.teamsName"
              value={(teacher.contacts && teacher.contacts.teamsName) || ""}
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
          <Grid item sm={8}>
            <TextField
              error={errorTeamsMail.error}
              helperText={errorTeamsMail.text}
              fullWidth
              variant="outlined"
              label="Teams el. paštas"
              id="contacts.teamsEmail"
              value={(teacher.contacts && teacher.contacts.teamsEmail) || ""}
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
          <Grid item sm={8}>
            <TextField
              error={errorHours.error}
              helperText={errorHours.text}
              fullWidth
              variant="outlined"
              label="Valandos per savaitę"
              id="workHoursPerWeek"
              value={teacher.workHoursPerWeek || ""}
              onBlur={(e) =>
                validate({
                  name: "workHours",
                  min: 0,
                  max: 54,
                  value: e.target.value,
                })
              }
              onChange={(e) => {
                handleChange(e);
              }}
            ></TextField>
          </Grid>
          <Grid item sm={8}>
            <FormControl
              required
              fullWidth
              error={errorShift.error}
              variant="outlined"
            >
              <InputLabel id="shift-label">Pamaina</InputLabel>
              <Select
                required
                fullWidth
                label="Galima pamaina"
                labelId="shift-label"
                id="teacher.selectedShift"
                value={
                  (teacher.selectedShift && teacher.selectedShift.id) || ""
                }
                onChange={(e) => {
                  handleShiftChange(e.target.value);
                }}
              >
                {shifts &&
                  shifts.map((shift) => (
                    <MenuItem key={shift.id} value={shift.id}>
                      {shift.name}
                    </MenuItem>
                  ))}
              </Select>
              {errorShift.error && (
                <FormHelperText>{errorShift.text}</FormHelperText>
              )}
            </FormControl>
          </Grid>
          {showSubjSelect && (
            <Grid item sm={8}>
              <FormControl fullWidth>
                <InputLabel id="subjects-label">Dalykai</InputLabel>
                <Select
                  label="Dalyko pavadinimas"
                  labelId="subject-label"
                  id="subject"
                  value={""}
                  defaultOpen={true}
                  onChange={(e) => {
                    handleAddChosen(e.target.value);
                  }}
                >
                  {freeSubjects &&
                    freeSubjects.map(
                      (
                        subject
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
            </Grid>
          )}
          {showSubjSelect && <Grid item sm={8}></Grid>}

          <Grid item sm={8}>
            <TableContainer component={Paper} style={{ width: "100%" }}>
              <Table
                style={{ tableLayout: "fixed" }}
                aria-label="custom pagination table"
              >
                <TableHead>
                  <TableRow>
                    <TableCell align="left">Dėstomi dalykai</TableCell>
                    <TableCell align="right">
                      <Button
                        id="add-subject-create-teacher"
                        variant="contained"
                        onClick={handleShowSubjects}
                      >
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
            {errorSubjectsList.error && (
              <Alert
                variant="outlined"
                severity="error"
                style={{ color: "#d32f2f" }}
              >
                {errorSubjectsList.text}
              </Alert>
            )}
          </Grid>
          <Grid item sm={12}>
            <Stack direction="row" spacing={2}>
              <Button
                id="save-button-teacher"
                variant="contained"
                onClick={preCreateCheck}
              >
                {mode === "update" ? "Išsaugoti" : "Išsaugoti"}
              </Button>
              {mode === "update" && (
                <Button
                  id="delete-button-edit-teacher"
                  variant="contained"
                  onClick={deleteTeacher}
                >
                  Ištrinti
                </Button>
              )}
              <Button
                id="back-button-teacher"
                variant="contained"
                onClick={() => navigate("/teachers")}
              >
                Grįžti
              </Button>
            </Stack>
          </Grid>
          <Grid item sm={8} marginBottom={10}>
            {errorMessage && <Alert severity="warning">{errorMessage}</Alert>}
            {createMessage && <Alert severity="success">{createMessage}</Alert>}
          </Grid>
        </Grid>
      </form>
    </Container>
  );
}
