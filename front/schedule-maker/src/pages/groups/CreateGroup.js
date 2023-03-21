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


  useEffect(() => {
    fetch(`api/v1/programs`)
      .then((response) => response.json())
      .then(setPrograms);
  }, []);

  useEffect(() => {
    fetch(`api/v1/shift/get-active`)
      .then((response) => response.json())
      .then(setShifts);
  }, []);

  const validation = () => {
    if (name === "" || schoolYear === "" || studentAmount === "" || program === "" || shift === "") {
      if (name === "") {
        setNameError(true);
      }
      else {
        setNameError(false);
      }
      if (schoolYear === "") {
        setYearError(true);
      }
      else {
        setYearError(false);
      }
      if (studentAmount === "") {
        setStudentAmountError(true);
      }
      else {
        setStudentAmountError(false);
      }
      if (program === "") {
        setProgramError(true);
      }
      else {
        setProgramError(false);
      }
      if (shift === "") {
        setShiftError(true);
      }
      else {
        setShiftError(false);
      }
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


  const badSymbols = "!@#$%^&*_+={}<>|~`\\'";
  const textLength = 200;

  const setNameAndCheck = (name) => {
    setName(name);

    (name.length === 0) ? setNameError(true) : setNameError(false);

    (name.length > textLength) ? setIsNameTooLong(true) : setIsNameTooLong(false);

    const isValid = name.split('').some(char => badSymbols.includes(char));
    (!isValid) ? setNameNotValid(false) : setNameNotValid(true);
  }

  const setSchoolYearAndCheck = (year) => {
    setSchoolYear(year);

    (year.length === 0) ? setYearError(true) : setYearError(false);

    (year.length > textLength) ? setIsYearTooLong(true) : setIsYearTooLong(false);

    const isValidYearString = /^[0-9\/-]+$/.test(year);
    (isValidYearString) ? setYearNotValid(false) : setYearNotValid(true);
  }

  const setStudentAmountAndCheck = (studentAmount) => {
    setStudentAmount(studentAmount);

    (studentAmount.length === 0) ? setStudentAmountError(true) : setStudentAmountError(false);

    (studentAmount.length > textLength) ? setIsStudentAmountTooLong(true) : setIsStudentAmountTooLong(false);

    const isDigitsOnly = /^[0-9]+$/.test(studentAmount);
    (isDigitsOnly) ? setStudentAmountNotValid(false) : setStudentAmountNotValid(true);
  }

  return (
    <div>
      <Container>
        <h3 className="create-header">Pridėti naują grupę</h3>
        <form>
          <Grid container rowSpacing={2}>
            <Grid item sm={8}>
              <TextField
                fullWidth
                required
                error={nameError || nameNotValid || isNameTooLong}
                helperText={
                  nameError
                    ? "Grupės pavadinimas yra privalomas"
                    : nameNotValid
                      ? "Laukas turi negalimų simbolių. "
                      : isNameTooLong
                        ? "Pavadinimas negali būti ilgesnis nei 200 simbolių"
                        : null
                }
                variant="outlined"
                label="Grupės pavadinimas"
                id="name"
                name="name"
                value={name}
                onChange={(e) => setNameAndCheck(e.target.value)}
              ></TextField>
            </Grid>

            <Grid item sm={8}>
              <TextField
                fullWidth
                required
                error={yearError || yearNotValid || isYearTooLong}
                helperText={
                  yearError
                    ? "Privaloma nurodyti mokslo metus."
                    : yearNotValid
                      ? "Laukas turi susidėti iš skaičių bei - ir / simbolių. "
                      : isYearTooLong
                        ? "Metai negali būti ilgesni nei 200 simbolių"
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

            <Grid item sm={8}>
              <TextField
                fullWidth
                required
                error={studentAmountError || studentAmountNotValid || isStudentAmountTooLong}
                helperText={
                  studentAmountError ?
                    "Privaloma nurodyti studentų kiekį."
                    : studentAmountNotValid
                      ? "Laukas turi susidėti iš skaičių."
                      : isStudentAmountTooLong
                        ? "Studentų kiekio laukas negali būti ilgesnis nei 200 skaičių"
                        : null
                }
                variant="outlined"
                label="Studentų kiekis"
                id="studentAmount"
                name="studentAmount"
                value={studentAmount}
                onChange={(e) => setStudentAmountAndCheck(e.target.value)}
              ></TextField>
            </Grid>

            <Grid item sm={8}>
              <FormControl fullWidth required error={programError}>
                <InputLabel id="program-label">
                  {programError
                    ? "Privaloma pasirinkti programą. "
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

            <Grid item sm={8}>
              <FormControl fullWidth required error={shiftError}>
                <InputLabel id="shift-label">
                  {shiftError
                    ? "Privaloma pasirinkti pamainą. "
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

            <Grid item sm={8} marginTop={2}>
              <Stack direction="row" spacing={2}>
                <Button variant="contained" onClick={validation}>
                  Išsaugoti
                </Button>

                <Link to="/groups">
                  <Button variant="contained">Grįžti</Button>
                </Link>
              </Stack>
            </Grid>
          </Grid>

          <Grid item sm={4}>
            {isPostUsed ? (
              successfulPost ? (
                <Alert severity="success"> Grupė sėkmingai pridėta.</Alert>
              ) :
                (
                  <Grid>
                    <Alert severity="warning">Nepavyko pridėti grupės.</Alert>
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
