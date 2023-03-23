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
import { Link, useHref, useParams } from "react-router-dom";

export function EditGroup() {
  const [group, setGroup] = useState({
    name: "",
    schoolYear: "",
    studentAmount: 0,
    program: {},
    shift: {},
  });
  const params = useParams();

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

  const listUrl = useHref("/groups");

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

  useEffect(() => {
    fetch("api/v1/group/view-group/" + params.id)
      .then((response) => response.json())
      .then((data) => {
        setGroup(data);
        setName(data.name);
        setSchoolYear(data.schoolYear);
        setStudentAmount(data.studentAmount);
        setProgram(data.program);
        setShift(data.shift);
      });
  }, []);

  const validation = () => {
    if (program === "") {
      setProgramError(true);
      return;
    }
    if (shift === "") {
      setShiftError(true);
      return;
    }
    if (
      nameError ||
      nameNotValid ||
      isNameTooLong ||
      yearError ||
      yearNotValid ||
      isYearTooLong ||
      studentAmountError ||
      studentAmountNotValid ||
      isStudentAmountTooLong
    ) {
      return;
    }
    editGroup();
  };

  const editGroup = () => {
    fetch("api/v1/group/modify-group/" + params.id, {
      method: "PUT",
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
      .then((response) => response.json())
      .then((data) => {
        handleAfterPost(data);
      });
  };

  const handleAfterPost = (data) => {
    if (data.valid) {
      setSuccessfulPost(true);
    } else {
      setGroupErrors(data);
      setSuccessfulPost(false);
    }
    setIsPostUsed(true);
  };

  const deleteGroup = (id) => {
    fetch("api/v1/group/deactivate-group/" + id, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
    }).then(() => (window.location = listUrl));
  };

  const badSymbols = "!@#$%^&*_+={}<>|~`\\'";
  const textLength = 200;

  const setNameAndCheck = (name) => {
    setName(name);

    name.length === 0 ? setNameError(true) : setNameError(false);

    name.length > textLength ? setIsNameTooLong(true) : setIsNameTooLong(false);

    const isValid = name.split("").some((char) => badSymbols.includes(char));
    !isValid ? setNameNotValid(false) : setNameNotValid(true);
  };

  const setSchoolYearAndCheck = (year) => {
    setSchoolYear(year);

    year.length === 0 ? setYearError(true) : setYearError(false);

    year.length > textLength ? setIsYearTooLong(true) : setIsYearTooLong(false);

    const isValidYearString = /^[0-9\/-]+$/.test(year);
    isValidYearString ? setYearNotValid(false) : setYearNotValid(true);
  };

  const setStudentAmountAndCheck = (studentAmount) => {
    setStudentAmount(studentAmount);

    studentAmount.length === 0
      ? setStudentAmountError(true)
      : setStudentAmountError(false);

    studentAmount.length > textLength
      ? setIsStudentAmountTooLong(true)
      : setIsStudentAmountTooLong(false);

    const isDigitsOnly = /^[0-9]+$/.test(studentAmount);
    isDigitsOnly
      ? setStudentAmountNotValid(false)
      : setStudentAmountNotValid(true);
  };

  return (
    <div>
      <Container>
        <h1 className="edit-header">Redagavimas</h1>
        <h3>{group.name}</h3>
        <span id="modified-date">
          Paskutinį kartą redaguota: {group.modifiedDate}
        </span>

        <form>
          <Grid container rowSpacing={3}>
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
                    ? "Laukas turi negalimų simbolių. "
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
                error={
                  studentAmountError ||
                  studentAmountNotValid ||
                  isStudentAmountTooLong
                }
                helperText={
                  studentAmountError
                    ? "Privaloma nurodyti studentų kiekį."
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
              <FormControl fullWidth>
                <InputLabel id="program-label">Programa</InputLabel>
                <Select
                  labelId="program-label"
                  id="program-select"
                  value={program.id || ""}
                  label="Programa"
                  onChange={(e) => {
                    const selectedPrograms = programs.find(
                      (s) => s.id === e.target.value
                    );
                    setProgram(selectedPrograms || {});
                  }}
                  error={programError}
                >
                  {programs.map((program) => (
                    <MenuItem key={program.id} value={program.id}>
                      {program.programName}
                    </MenuItem>
                  ))}
                </Select>
                {programError && (
                  <Alert severity="error">Reikia pasirinkti programą</Alert>
                )}
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
                  value={shift.id || ""}
                  onChange={(e) => {
                    const selectedShift = shifts.find(
                      (s) => s.id === e.target.value
                    );
                    setShift(selectedShift || {});
                  }}
                >
                  {shifts.map((shift) => (
                    <MenuItem key={shift.id} value={shift.id}>
                      {shift.name}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Grid>

            <Grid item sm={8}>
              <Stack direction="row" spacing={2}>
                <Button
                  id="save-button-edit-group"
                  variant="contained"
                  onClick={validation}
                >
                  Išsaugoti
                </Button>

                <Button
                  id="delete-button-edit-group"
                  variant="contained"
                  onClick={() => deleteGroup(group.id)}
                >
                  Ištrinti
                </Button>

                <Link to="/groups">
                  <Button id="back-button-edit-group" variant="contained">
                    Grįžti
                  </Button>
                </Link>
              </Stack>
            </Grid>

            <Grid item sm={8}>
              {isPostUsed ? (
                successfulPost ? (
                  <Alert severity="success"> Grupė sėkmingai pakeista.</Alert>
                ) : (
                  <Grid>
                    <Alert severity="warning">Nepavyko pakeisti grupės.</Alert>
                    {groupErrors.passedValidation
                      ? groupErrors.databaseErrors.map(
                          (databaseError, index) => (
                            <Alert key={index} severity="warning">
                              {databaseError}
                            </Alert>
                          )
                        )
                      : Object.keys(groupErrors.validationErrors).map((key) => (
                          <Alert key={key} severity="warning">
                            {" "}
                            {groupErrors.validationErrors[key]} {key} laukelyje.
                          </Alert>
                        ))}
                  </Grid>
                )
              ) : (
                <div></div>
              )}
            </Grid>
          </Grid>
        </form>
      </Container>
    </div>
  );
}
