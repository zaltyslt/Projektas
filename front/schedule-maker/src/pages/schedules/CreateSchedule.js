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
import { Link } from "react-router-dom";
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

  useEffect(() => fetchGroups, []);

  const fetchGroups = () => {
    fetch("api/v1/group/get-active")
      .then((response) => response.json())
      .then(setGroups);
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
  };

  return (
    <div>
      <Container>
        <h3>Sukurti naują tvarkaraštį</h3>
        <form>
          <Grid container rowSpacing={2} spacing={2}>
            <Grid item sm={10}>
              <FormControl fullWidth required>
                <InputLabel id="group-label">Gupės pavadinimas</InputLabel>
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
                value={schoolYear}
                onChange={(e) => setSchoolYear(e.target.value)}
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
                value={semester}
                onChange={(e) => setSemester(e.target.value)}
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
                <Button variant="contained" onClick={createSchedule}>
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
