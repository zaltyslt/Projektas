import {
  Alert,
  Button,
  FormControl,
  FormHelperText,
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
import { Link } from "react-router-dom";
import { dateToUtc } from "../../helpers/helpers";
import "./Schedule.css";

export function CreateSchedule() {
  const [groups, setGroups] = useState([]);
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
  const [schoolYearValid, setSchoolYearValid] = useState(false);
  const [dateFromEmpty, setDateFromEmpty] = useState(false);
  const [dateUntilEmpty, setDateUntilEmpty] = useState(false);

  const [errorMessageFrom, setErrorMessageFrom] = useState("");
  const [errorMessageUntil, setErrorMessageUntil] = useState("");
  const [errorLengthName, setErrorLengthName] = useState(false);
  const [errorLengthYear, setErrorLengthYear] = useState(false);

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

    const isValid =  /^[0-9\/-]+$/.test(value);
    isValid ? setSchoolYearValid(false) : setSchoolYearValid(true);
  };

  const validateGroup = (value) => {
    setGroup(value);
    value.length === 0 ? setGroupEmpty(true) : setGroupEmpty(false);
  };

  const validateDateFrom = (value) => {
    setDateFrom(dateToUtc(value));
    if (value.length === 0) {
      setDateFromEmpty(true);
    } else {
      setDateFromEmpty(false);
      setErrorMessageFrom("");
    }
  };

  const validateDateUntil = (value) => {
    setDateUntil(dateToUtc(value));
    if (value.length === 0) {
      setDateUntilEmpty(true);
    } else {
      setDateUntilEmpty(false);
      setErrorMessageUntil("");
    }
  };

  const validation = () => {
    let isValid = true;
    if (group === "" || group === "undefined") {
      setGroupEmpty(true);
      isValid = false;
    }

    if (schoolYear === "" || schoolYear === "undefined") {
      setSchoolYearEmpty(true);
      isValid = false;
    }

    if (semester === "" || semester === "undefined") {
      setSemesterEmpty(true);
      isValid = false;
    }

    if (dateFrom === "" || dateFrom === "undefined") {
      setDateFromEmpty(true);
      setErrorMessageFrom("Privaloma pasirinkti pradžios datą.");
      isValid = false;
    } else {
      setDateFromEmpty(false);
    }

    if (dateUntil === "" || dateUntil === "undefined") {
      setDateUntilEmpty(true);
      setErrorMessageUntil("Privaloma pasirinkti pabaigos datą.");
      isValid = false;
    } else {
      setDateUntilEmpty(false);
    }

    if (dateFrom !== "" && dateUntil !== "" && dateFrom.isAfter(dateUntil)) {
      setErrorMessageUntil("Diena iki negali būti vėliau už dieną nuo.");
      setDateUntilEmpty(true);
      isValid = false;
    } 

    if (dateFrom !== "" && dateUntil !== "" && dateFrom.$d.toISOString().split('T')[0] === dateUntil.$d.toISOString().split('T')[0]) {
      setErrorMessageUntil("Pradžios ir pabaigos data negali būti ta pati diena.");
      setDateUntilEmpty(true);
      isValid = false;
    }

    if (isValid) {
      setCreateMessage("");
      createSchedule();
    }
  };

  const createSchedule = () => {
    fetch(`/api/v1/schedules/create-schedule?groupId=${group.id}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
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
    fetchGroups();
    setGroup("");
    setSchoolYear("");
    setSemester("");
    setDateFrom("");
    setDateUntil("");
    setGroupEmpty(false);
    setError("");
  };

  return (
    <div>
      <Container>
        <h3>Sukurti naują tvarkaraštį</h3>
        <form>
          <Grid container rowSpacing={2} spacing={2}>
            <Grid item sm={10}>
              <FormControl fullWidth required error={groupEmpty}>
                <InputLabel id="group-label">Gupės pavadinimas</InputLabel>
                <Select
                  label="Grupės pavadinimas"
                  labelId="group-label"
                  id="group"
                  error={groupEmpty}
                  value={group}
                  onChange={(e) => {
                    validateGroup(e.target.value);
                  }}
                >
                  {groups.map((group) => (
                    <MenuItem key={group.id} value={group}>
                      {group.name}
                    </MenuItem>
                  ))}
                </Select>
                {groupEmpty && (
                  <FormHelperText error>
                    Privaloma pasirinkti grupę.
                  </FormHelperText>
                )}
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
                error={schoolYearValid || schoolYearEmpty || errorLengthYear}
                helperText={
                  schoolYearValid
                    ? "Laukas gali susidėti iš skaičių bei - ar / simbolių."
                    : schoolYearEmpty
                    ? "Mokslo metai yra privalomi."
                    : errorLengthYear
                    ? "Mokslo metai negali būti ilgesnis nei 200 simbolių."
                    : ""
                }
                value={schoolYear}
                onChange={(e) => {
                  const input = e.target.value;
                  if (input.length > 200) {
                    setErrorLengthYear(true);
                  } else {
                    setErrorLengthYear(false);
                  }
                  validateSchoolYear(input);
                }}
              ></TextField>
            </Grid>

            <Grid item sm={10}>
              <TextField
                fullWidth
                required
                variant="outlined"
                label="Pavadinimas (pvz. pusmetis, mėnuo, savaitė, kt.)"
                id="semester"
                name="semester"
                error={!semesterValid || semesterEmpty || errorLengthName}
                helperText={
                  !semesterValid
                    ? "Pavadinimas turi neleidžiamų simbolių."
                    : semesterEmpty
                    ? "Pavadinimas yra privalomas."
                    : errorLengthName
                    ? "Pavadinimas negali būti ilgesnis nei 200 simbolių."
                    : ""
                }
                value={semester}
                onChange={(e) => {
                  const input = e.target.value;
                  if (input.length > 200) {
                    setErrorLengthName(true);
                  } else {
                    setErrorLengthName(false);
                  }
                  validateSemester(input);
                }}
              ></TextField>
            </Grid>

            <Grid item sm={5}>
              <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DatePicker
                  className="DatePicker"
                  label="Nuo"
                  format="YYYY/MM/DD"
                  value={dateFrom}
                  onChange={(e) => validateDateFrom(e)}
                  slotProps={{
                    textField: {
                      helperText: errorMessageFrom,
                      error: dateFromEmpty,
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
                  onChange={(e) => validateDateUntil(e)}
                  slotProps={{
                    textField: {
                      helperText: errorMessageUntil,
                      error: dateUntilEmpty,
                    },
                  }}
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
