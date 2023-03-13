import {
  Alert,
  Button,
  FormControl,
  Grid,
  InputLabel,
  FormHelperText,
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
  const [group, setGroup] = useState("");
  const [schoolYear, setSchoolYear] = useState("");
  const [semester, setSemester] = useState("");
  const [dateFrom, setDateFrom] = useState("");
  const [dateUntil, setDateUntil] = useState("");

  const [error, setError] = useState(false);
  const [createMessage, setCreateMessage] = useState("");

  const [semesterEmpty, setSemesterEmpty] = useState(false);
  const [semesterValid, setSemesterValid] = useState(true);
  const [groupEmpty, setGroupEmpty] = useState(false);
  const [schoolYearEmpty, setSchoolYearEmpty] = useState(false);
  const [schoolYearValid, setSchoolYearValid] = useState(true);
  const [dateFromEmpty, setDateFromEmpty] = useState(false);
  const [dateUntilEmpty, setDateUntilEmpty] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [errorLengthName, setErrorLengthName] = useState(false);
  const [errorLengthYear, setErrorLengthYear] = useState(false);

  const badSymbols = "!@#$%^&*_+={}<>|~`\\'";

  useEffect(() => fetchGroups, []);
function sw(){
  setDateFromEmpty(!dateFromEmpty);
  setDateUntilEmpty(!dateUntilEmpty);
}

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

  const validateGroup = (value) => {
    setGroup(value);
    value.length === 0 ? setGroupEmpty(true) : setGroupEmpty(false);
  };

  const dateToUtc = (date)=>{
    const utcDate = dayjs(date.$d.getTime() - (date.$d.getTimezoneOffset() * 60 * 1000));
    return utcDate;
  }
  const validateDateFrom = (value) => {
     const tempDate = dateToUtc(value);
    setDateFrom(tempDate);
    if (value.length === 0) {
      setDateFromEmpty(true);
      setErrorMessage("Privaloma užpildyti");
    } else {
      setDateFromEmpty(false);
      setErrorMessage("");
    }
  };
  const validateDateUntil = (value) => {
    const tempDate = dateToUtc(value);
     setDateUntil(tempDate);
    if (value.length === 0) {
      setDateUntilEmpty(true);
      setErrorMessage("Privaloma užpildyti");
    } else {
      setDateFromEmpty(false);
      setErrorMessage("");
    }
  };

  const validation = () => {
    if (
      // group === "" &&
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
      setErrorMessage("Privaloma užpildyti");
    } else if (
      // group === "" ||
      schoolYear === "" ||
      semester === "" ||
      dateFrom === "" ||
      dateUntil === ""
    ) {
    } else {
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
    setErrorMessage("");
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
                error={!schoolYearValid || schoolYearEmpty || errorLengthYear}
                helperText={
                  !schoolYearValid
                    ? "Mokslo metai turi neleidžiamų simbolių."
                    : schoolYearEmpty
                      ? "Mokslo metai negali būti tušti"
                      : errorLengthYear
                      ? "Mokslo metai negali būti ilgesnis nei 200 simbolių"
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
                // onChange={(e) => validateSchoolYear(e.target.value)}
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
                      ? "Pavadinimas negali būti tuščias"
                                          : errorLengthName
                    ? "Pavadinimas negali būti ilgesnis nei 200 simbolių"
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
                // onChange={(e) => validateSemester(e.target.value)}
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
                      helperText: errorMessage,
                      error: dateFromEmpty,
                      
                    },
                  }}
                ></DatePicker>
                {dateFromEmpty && (<FormHelperText error>Privaloma pasirinkti data</FormHelperText> )}
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
                      
                      helperText: errorMessage,
                      error: dateUntilEmpty,
                    },
                  }}
                ></DatePicker>
                 {dateUntilEmpty && (<FormHelperText error>Privaloma pasirinkti data</FormHelperText> )}
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
                <Button variant="contained" onClick={sw}>
                  Switch
                </Button>
              </Stack>
            </Grid>
          </Grid>
        </form>
      </Container>
    </div>
  );
}
