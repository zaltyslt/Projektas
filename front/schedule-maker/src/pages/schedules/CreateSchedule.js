import {
  Alert,
  Button,
  FormControl,
  Grid,
  InputLabel,
  MenuItem,
  Select,
  Stack,
  TextField,
} from "@mui/material";
import { Container } from "@mui/system";
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { useEffect, useState } from "react";
import { Link, useAsyncError } from "react-router-dom";
import "./Schedule.css";

export function CreateSchedule() {
  const [groups, setGroups] = useState([]);
  const [selectedGroup, setSelectedGroup] = useState("");
  const [group, setGroup] = useState("");
  const [schoolYear, setSchoolYear] = useState("");
  const [semester, setSemester] = useState("");
  const [dateFrom, setDateFrom] = useState("");
  const [dateUntil, setDateUntil] = useState("");

  const [error, setError] = useState("");
  const [createMessage, setCreateMessage] = useState("");

  const [semesterEmpty, setSemesterEmpty] = useState(false);
  const [semesterValid, setSemesterValid] = useState(true);
  const [groupEmpty, setGroupEmpty] = useState(false);
  const [schoolYearEmpty, setSchoolYearEmpty] = useState(false);
  const [schoolYearValid, setSchoolYearValid] = useState(true);
  const [dateFromEmpty, setDateFromEmpty] = useState(false);
  const [dateUntilEmpty, setDateUntilEmpty] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const badSymbols = "!@#$%^&*_+={}<>|~`\\'";

  useEffect(() => fetchGroups, []);

  const fetchGroups = () => {
    fetch("api/v1/group/get-active/small")
      .then((response) => response.json())
      .then(setGroups);
  };

  const validateSemester = (value) => {
    setSemester(value);
    value.length === 0 ? setSemesterEmpty(true) : setSemesterEmpty(false);

    const isValid = value.split("").some((char) => badSymbols.includes(char));
    isValid ? setSemesterValid(false) : setSemesterValid(true);
  };

  const validateSchoolYear = (value) => {
    setSchoolYear(value);
    value.length === 0 ? setSchoolYearEmpty(true) : setSchoolYearEmpty(false);

    const isValid = value.split("").some((char) => badSymbols.includes(char));
    isValid ? setSchoolYearValid(false) : setSchoolYearValid(true);
  };

  const validation = () => {
    if (
      selectedGroup === "" &&
      schoolYear === "" &&
      semester === "" &&
      dateFrom === "" &&
      dateUntil === ""
    ) {
      setSemesterEmpty(true);
      setGroupEmpty(true);
      setSchoolYearEmpty(true);
      setDateFromEmpty(true);
      setDateUntilEmpty(true);
      setErrorMessage("Privaloma");
    } else if (selectedGroup === "") {
      setGroupEmpty(true);
    } else if (dateFrom === "") {
      setDateFromEmpty(true);
      setErrorMessage("Privaloma");
    } else if (dateUntil === "") {
      setDateUntilEmpty(true);
    } else {
      createSchedule();
    }
  };

  const createSchedule = () => {
    fetch("/api/v1/schedule/create", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        group,
        schoolYear,
        semester,
        dateFrom,
        dateUntil,
      }),
    }).then((response) => {
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
    setGroups(fetchGroups());
    setSelectedGroup("");
    setSchoolYear("");
    setSemester("");
    setDateFrom("");
    setDateUntil("");
    setGroupEmpty(false);
  };

  return (
    <div>
      <Container>
        <h3>Sukurti naują tvarkaraštį</h3>
        <form>
          <Grid container rowSpacing={2} spacing={2}>
            <Grid item sm={10}>
              <FormControl fullWidth required error={groupEmpty}>
                <InputLabel id="group-label">
                  {groupEmpty
                    ? "Privaloma pasirinkti grupę"
                    : "Gupės pavadinimas"}
                </InputLabel>
                <Select
                  label="Grupės pavadinimas"
                  labelId="group-label"
                  id="group"
                  value={selectedGroup}
                  onChange={(e) => {
                    setSelectedGroup(e.target.value);
                    setGroup(e.target.value.id);
                  }}
                >
                  {groups.map((group) => (
                    <MenuItem key={group.id} value={group}>
                      {group.name}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </Grid>

            <Grid item sm={10}>
              <TextField
                fullWidth
                required
                variant="outlined"
                label="Mokslo metai"
                id="schoolYear"
                name="schoolYear"
                error={!schoolYearValid || schoolYearEmpty}
                helperText={
                  !schoolYearValid
                    ? "Mokslo metai turi neleidžiamų simbolių."
                    : schoolYearEmpty
                    ? "Mokslo metai negali būti tušti"
                    : ""
                }
                value={schoolYear}
                onChange={(e) => validateSchoolYear(e.target.value)}
              ></TextField>
            </Grid>

            <Grid item sm={10}>
              <TextField
                fullWidth
                required
                variant="outlined"
                label="Sesija"
                id="semester"
                name="semester"
                error={!semesterValid || semesterEmpty}
                helperText={
                  !semesterValid
                    ? "Semestro pavadinimas turi neleidžiamų simbolių."
                    : semesterEmpty
                    ? "Semestro pavadinimas negali būti tuščias"
                    : ""
                }
                value={semester}
                onChange={(e) => validateSemester(e.target.value)}
              ></TextField>
            </Grid>

            <Grid item sm={5}>
              <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DatePicker
                  className="DatePicker"
                  label="Nuo"
                  format="YYYY/MM/DD"
                  value={dateFrom}
                  onChange={(e) => setDateFrom(e)}
                  slotProps={{
                    textField: {
                      helperText: errorMessage,
                    },
                  }}
                ></DatePicker>
              </LocalizationProvider>
            </Grid>

            <Grid item sm={5}>
              <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DatePicker
                  className="DatePicker"
                  label="Iki"
                  format="YYYY/MM/DD"
                  value={dateUntil}
                  onChange={(e) => setDateUntil(e)}
                ></DatePicker>
              </LocalizationProvider>
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

                <Link to="/">
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
