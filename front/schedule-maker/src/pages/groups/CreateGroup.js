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
  const [year, setYear] = useState("");
  const [studentAmount, setStudentAmount] = useState("");
  const [program, setProgram] = useState("");
  const [shift, setShift] = useState("");

  const [programs, setPrograms] = useState([]);
  const [shifts, setShifts] = useState([]);

  const [nameError, setNameError] = useState(false);
  const [yearError, setYearError] = useState(false);
  const [studentAmountError, setStudentAmountError] = useState(false);
  const [programError, setProgramError] = useState(false);
  const [shiftError, setShiftError] = useState(false);

  const [nameNotValid, setNameNotValid] = useState(false);
  const [yearNotValid, setYearNotValid] = useState(false);

  const [error, setError] = useState("");
  const [createMessage, setCreateMessage] = useState("");

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
    setCreateMessage("");
    const badSymbols = "!@#$%^&*_+={}<>|~`\\'";
    let notValidName = name.split("").some((char) => badSymbols.includes(char));
    let notValidYear = year.split("").some((char) => badSymbols.includes(char));

    if (
      name === "" &&
      year === "" &&
      studentAmount === "" &&
      program === "" &&
      shift === ""
    ) {
      setNameError(true);
      setYearError(true);
      setStudentAmountError(true);
      setProgramError(true);
      setShiftError(true);
    } else if (name === "") {
      setNameError(true);
    } else if (notValidName) {
      setNameError(false);
      setNameNotValid(true);
    } else if (year === "") {
      setYearError(true);
    } else if (notValidYear) {
      setYearError(false);
      setYearNotValid(true);
    } else if (studentAmount === "") {
      setStudentAmountError(true);
    } else if (program === "") {
      setProgramError(true);
    } else if (shift === "") {
      setShiftError(true);
    } else {
      createGroup();
    }
  };

  const createGroup = () => {
    fetch("api/v1/group/add-group", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name,
        year,
        studentAmount,
        program,
        shift,
      }),
    }).then((response) => {
      // let statusCode = response.status;
      let success = response.ok;

      response.json().then((response) => {
        if (!success) {
          setCreateMessage("");
          setError(response.message);
        } else {
          setCreateMessage("Sėkmingai sukurta. ");
          setError("");
          clear();
        }
      });
    });
  };

  const clear = () => {
    setName("");
    setYear("");
    setStudentAmount("");
    fetchPrograms();
    fetchShifts();
    setNameError(false);
    setYearError(false);
    setStudentAmountError(false);
    setProgramError(false);
    setShiftError(false);
  };

  return (
    <div>
      <Container>
        <h3>Pridėti naują grupę</h3>
        <form>
          <Grid container rowSpacing={2}>
            <Grid item sm={10}>
              <TextField
                fullWidth
                required
                error={nameError || nameNotValid}
                helperText={
                  nameError
                    ? "Grupės pavadinimas yra privalomas"
                    : nameNotValid
                    ? "Laukas turi negalimų simbolių. "
                    : ""
                }
                variant="outlined"
                label="Grupės pavadinimas"
                id="name"
                name="name"
                value={name}
                onChange={(e) => setName(e.target.value)}
              ></TextField>
            </Grid>

            <Grid item sm={10}>
              <TextField
                fullWidth
                required
                error={yearError || yearNotValid}
                helperText={
                    yearError
                      ? "Privaloma nurodyti mokslo metus."
                      : yearNotValid
                      ? "Laukas turi negalimų simbolių. "
                      : ""
                  }
                variant="outlined"
                label="Mokslo metai"
                id="year"
                name="year"
                value={year}
                onChange={(e) => setYear(e.target.value)}
              ></TextField>
            </Grid>

            <Grid item sm={10}>
              <TextField
                fullWidth
                required
                error={studentAmountError}
                helperText={
                  studentAmountError && "Privaloma nurodyti studentų kiekį."
                }
                variant="outlined"
                label="Studentų kiekis"
                id="studentAmount"
                name="studentAmount"
                value={studentAmount}
                onChange={(e) => setStudentAmount(Number(e.target.value))}
              ></TextField>
            </Grid>

            <Grid item sm={10}>
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

            <Grid item sm={10}>
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

            <Grid item sm={10}>
              {error && <Alert severity="warning">{error}</Alert>}
              {createMessage && (
                <Alert severity="success">{createMessage}</Alert>
              )}
            </Grid>

            <Grid item sm={10}>
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
        </form>
      </Container>
    </div>
  );
}
