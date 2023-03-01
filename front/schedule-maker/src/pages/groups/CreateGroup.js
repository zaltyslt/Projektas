import {
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
  const [year, setYear] = useState(2023);
  const [studentAmount, setStudentAmount] = useState(0);
  const [program, setProgram] = useState("");
  const [shift, setShift] = useState("");

  const [programs, setPrograms] = useState([]);
  const [shifts, setShifts] = useState([]);

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
    createGroup();
  };

  const createGroup = () => {
    fetch("api/v1/groups/add-group", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        name,
        year,
        studentAmount,
        program,
        shift
      }),
    });
  };

  const clear = () => {
    setName("");
    setYear(2023);
    setStudentAmount(0);
    fetchPrograms();
    fetchShifts();
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
                variant="outlined"
                label="Studentų kiekis"
                id="studentAmount"
                name="studentAmount"
                value={studentAmount}
                onChange={(e) => setStudentAmount(Number(e.target.value))}
              ></TextField>
            </Grid>

            <Grid item sm={10}>
              <FormControl fullWidth required>
                <InputLabel id="program-label">Programa</InputLabel>
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
              <FormControl fullWidth required>
                <InputLabel id="shift-label">Pamaina</InputLabel>
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
