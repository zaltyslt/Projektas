import {
  Alert,
  Button,
  FormControl,
  Grid,
  InputLabel,
  MenuItem,
  Select,
  TextField,
} from "@mui/material";
import { Container, Stack } from "@mui/system";
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

export function CreateGroup() {
  const [name, setName] = useState("");
  const [schoolYear, setSchoolYear] = useState("");
  const [studentAmount, setStudentAmount] = useState("");
  const [program, setProgram] = useState("");
  const [shift, setShift] = useState("");

  const [programs, setPrograms] = useState([]);
  const [shifts, setShifts] = useState([]);

  const [programError, setProgramError] = useState(false);
  const [shiftError, setShiftError] = useState(false);

  const [nameError, setNameError] = useState(false);
  const [nameNotValid, setNameNotValid] = useState(false);
  const [isNameTooLong, setIsNameTooLong] = useState(false);

  const [yearError, setYearError] = useState(false);
  const [yearNotValid, setYearNotValid] = useState(false);
  const [isYearTooLong, setIsYearTooLong] = useState(false);

  const [studentAmountError, setStudentAmountError] = useState(false);
  const [studentAmountNotValid, setStudentAmountNotValid] = useState(false);
  const [isStudentAmountTooLong, setIsStudentAmountTooLong] = useState(false);

  const [successfulPost, setSuccessfulPost] = useState();
  const [isPostUsed, setIsPostUsed] = useState(false);
  const [groupErrors, setGroupErrors] = useState();

  useEffect(() => fetchPrograms, []);

  useEffect(() => fetchShifts, []);

  const fetchPrograms = () => {
    fetch("api/v1/programs")
      .then((response) => response.json())
      .then(setPrograms);
  };

  const fetchShifts = () => {
    fetch("api/v1/shift/get-active")
      .then((response) => response.json())
      .then(setShifts);
  };

  const validation = () => {
    if (program === "") {
      setProgramError(true);
      return;
    }
    if (shift === "") {
      setShiftError(true);
      return;
    }
    if (nameError || nameNotValid || isNameTooLong || yearError || yearNotValid || isYearTooLong || studentAmountError || studentAmountNotValid || isStudentAmountTooLong) {
      return;
    }
    createGroup();
  };

  const createGroup = () => {
    fetch("api/v1/group/add-group", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name,
        schoolYear,
        studentAmount,
        program,
        shift,
      }),
    })
    .then(response => response.json())
    .then(data => {
        handleAfterPost(data);
  })
};

const handleAfterPost = ((data) => {
    if (data.valid) {
        setSuccessfulPost(true);
    }
    else {
        setGroupErrors(data)
        setSuccessfulPost(false);
    }
    setIsPostUsed(true);
})

  const clear = () => {
    setName("");
    setSchoolYear("");
    setStudentAmount("");
    fetchPrograms();
    fetchShifts();
    setNameError(false);
    setYearError(false);
    setStudentAmountError(false);
    setProgramError(false);
    setShiftError(false);
    setNameNotValid(false);
    setYearNotValid(false);
  };

  const badSymbols = "!@#$%^&*_+={}<>|~`\\'";
  const textLength = 200;

  const setNameAndCheck = (name) => {
    setName(name);

    (name.length === 0) ? setNameError(true) :  setNameError(false);

    (name.length > textLength) ? setIsNameTooLong(true) : setIsNameTooLong(false);

    const isValid = name.split('').some(char => badSymbols.includes(char));
    (!isValid) ? setNameNotValid(false) : setNameNotValid(true);
  }

  const setSchoolYearAndCheck = (year) => {
    setSchoolYear(year);

    (year.length === 0) ? setYearError(true) :  setYearError(false);

    (year.length > textLength) ? setIsYearTooLong(true) : setIsYearTooLong(false);

    const isValidYearString = /^[0-9\/-]+$/.test(year);
    (isValidYearString) ? setYearNotValid(false) : setYearNotValid(true);
  }

  const setStudentAmountAndCheck = (studentAmount) => {
    setStudentAmount(studentAmount);

    (studentAmount.length === 0) ? setStudentAmountError(true) :  setStudentAmountError(false);

    (studentAmount.length > textLength) ? setIsStudentAmountTooLong(true) : setIsStudentAmountTooLong(false);

    const isDigitsOnly = /^[0-9]+$/.test(studentAmount);
    (isDigitsOnly) ? setStudentAmountNotValid(false) : setStudentAmountNotValid(true);
  }

  return (
    <div>
      <Container>
        <h3>Prid??ti nauj?? grup??</h3>
        <form>
          <Grid container rowSpacing={2}>
            <Grid item sm={10}>
              <TextField
                fullWidth
                required
                error={nameError || nameNotValid || isNameTooLong}
                helperText={
                  nameError
                    ? "Grup??s pavadinimas yra privalomas"
                    : nameNotValid
                    ? "Laukas turi negalim?? simboli??. "
                    : isNameTooLong
                    ? "Pavadinimas negali b??ti ilgesnis nei 200 simboli??"
                    : null
                }
                variant="outlined"
                label="Grup??s pavadinimas"
                id="name"
                name="name"
                value={name}
                onChange={(e) => setNameAndCheck(e.target.value)}
              ></TextField>
            </Grid>

            <Grid item sm={10}>
              <TextField
                fullWidth
                required
                error={yearError || yearNotValid || isYearTooLong}
                helperText={
                    yearError
                      ? "Privaloma nurodyti mokslo metus."
                      : yearNotValid
                      ? "Laukas turi susid??ti i?? skai??i?? bei - ir / simboli??. "
                      : isYearTooLong
                      ? "Metai negali b??ti ilgesni nei 200 simboli??"
                      : null
                  }
                variant="outlined"
                label="Mokslo metai"
                id="schoolYear"
                name="schoolYear"
                value={schoolYear}
                onChange={(e) => setSchoolYearAndCheck(e.target.value)}
              ></TextField>
            </Grid>

            <Grid item sm={10}>
              <TextField
                fullWidth
                required
                error={studentAmountError || studentAmountNotValid || isStudentAmountTooLong}
                helperText={
                  studentAmountError ?
                   "Privaloma nurodyti student?? kiek??."
                   : studentAmountNotValid
                   ? "Laukas turi susid??ti i?? skai??i??."
                   : isStudentAmountTooLong
                   ? "Student?? kiekio laukas negali b??ti ilgesnis nei 200 skai??i??"
                   : null
                }
                variant="outlined"
                label="Student?? kiekis"
                id="studentAmount"
                name="studentAmount"
                value={studentAmount}
                onChange={(e) => setStudentAmountAndCheck(e.target.value)}
              ></TextField>
            </Grid>

            <Grid item sm={10}>
              <FormControl fullWidth required error={programError}>
                <InputLabel id="program-label">
                  {programError
                    ? "Privaloma pasirinkti program??. "
                    : "Programos pavadinimas"}
                </InputLabel>
                <Select
                  label="Programos pavadinimas"
                  labelId="program-label"
                  id="program"
                  value={program}
                  onChange={(e) => {
                    setProgram(e.target.value);
                  }}
                >
                  {programs.map((program) => (
                    <MenuItem key={program.id} value={program}>
                      {program.programName}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Grid>

            <Grid item sm={10}>
              <FormControl fullWidth required error={shiftError}>
                <InputLabel id="shift-label">
                  {shiftError
                    ? "Privaloma pasirinkti pamain??. "
                    : "Pamainos pavadinimas"}
                </InputLabel>
                <Select
                  label="Pamainos pavadinimas"
                  labelId="shift-label"
                  id="shift"
                  value={shift}
                  onChange={(e) => {
                    setShift(e.target.value);
                  }}
                >
                  {shifts.map((shift) => (
                    <MenuItem key={shift.id} value={shift}>
                      {shift.name}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Grid>

            <Grid item sm={10}>
              <Stack direction="row" spacing={2}>
                <Button variant="contained" onClick={validation}>
                  I??saugoti
                </Button>

                <Link to="/groups">
                  <Button variant="contained">Gr????ti</Button>
                </Link>
              </Stack>
            </Grid>
          </Grid>

          <Grid item sm={10}>
                {isPostUsed ? (
                    successfulPost ? (
                        <Alert severity="success"> Grup?? s??kmingai prid??ta.</Alert>
                        ) : 
                        (
                        <Grid>
                            <Alert severity="warning">Nepavyko prid??ti grup??s.</Alert>
                            {
                                (groupErrors.passedValidation ?
                                    (groupErrors.databaseErrors).map((databaseError, index) => (
                                        <Alert key={index} severity="warning">
                                        {databaseError}
                                        </Alert>
                                    )) 
                                    :
                                    Object.keys(groupErrors.validationErrors).map(key => (
                                    <Alert key={key} severity="warning"> {groupErrors.validationErrors[key]} {key} laukelyje.
                                    </Alert>
                                    ))
                                )
                            }
                        </Grid>
                        )
                    ) : 
                    (
                    <div></div>
                    )}
          </Grid>         
        </form>
      </Container>
    </div>
  );
}
