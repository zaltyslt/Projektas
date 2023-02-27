import {
  Alert,
  Button,
  Container,
  Grid,
  Stack,
  TextField,
} from "@mui/material";
import { useState } from "react";
import { Link } from "react-router-dom";
import ".././pages.css";
import { useNavigate } from "react-router-dom";

export function CreateTeacher() {
  const [fName, setFName] = useState("");
  const [lName, setLName] = useState("");
  const [nickName, setNickName] = useState("");
  const [active, setActive] = useState("");
  const [phone_number, setPhone_number] = useState("");
  const [direct_email, setDirect_email] = useState("");
  const [teams_name, setTeams_name] = useState("");
  const [teams_email, setTeams_email] = useState("");
  const [workHoursPerWeek, setWorkHoursPerWeek] = useState("");
  let navigate = useNavigate();

  const clear = () => {
    setFName("");
    setLName("");
    setNickName("");
    setActive("");
    setPhone_number("");
    setDirect_email("");
    setTeams_email("");
    setTeams_name("");
    setWorkHoursPerWeek("");
  };

  const isActive = [
    {value: "true", label: "aktyvus"},
    {value: "true", label: "neaktyvus"}
  ];

  const applyResult = (result) => {
    if (result.ok) {
      setSuccess("Sėkmingai pridėta!");
      clear();
    } else {
      setError("Nepavyko sukurti!");
    }
  };

  const createTeacher = () => {
    fetch("/api/v1/teachers", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        fName,
        lname,
        nickName,
      }),
    }).then(applyResult);
  };

  return (
    <Container>
      <h3 className="create-header">Pridėti naują dėstytoją</h3>
      <form>
        <Grid container rowSpacing={2}>
          <Grid item sm={5} id="grid-selector">
            <TextField
              fullWidth
              required
              variant="outlined"
              label="Vardas"
              id="fname"
              value={fName}
              onChange={(e) => setFName(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item sm={5}>
            <TextField
              fullWidth
              required
              variant="outlined"
              label="Pavardė"
              id="lname"
              value={lName}
              onChange={(e) => setLName(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item sm={5} id="grid-selector">
            <TextField
              fullWidth
              required
              variant="outlined"
              label="Nickas"
              id="nickName"
              value={nickName}
              onChange={(e) => setNickName(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item sm={5}>
          <Select
              fullWidth
              multiline
              variant="outlined"
              label="Būsena"
              id="active"
              value={active}
              onChange={(e) => setActive(e.target.value)}
            >
              {isActive.map((isActive) => (
                <MenuItem key={isActive.value} value={isActive.value}>
                  {isActive.label}
                </MenuItem>
              ))}
            </Select>
          </Grid>

          <Grid item sm={5} id="grid-selector">
            <TextField
              fullWidth
              required
              variant="outlined"
              label="Kontaktinis telefonas"
              id="phone_number"
              value={phone_number}
              onChange={(e) => setPhone_number(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item sm={5}>
            <TextField
              fullWidth
              required
              variant="outlined"
              label="El. paštas"
              id="direct_email"
              value={direct_email}
              onChange={(e) => setDirect_email(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item sm={5} id="grid-selector">
            <TextField
              fullWidth
              required
              variant="outlined"
              label="Teams vardas"
              id="teams_name"
              value={teams_name}
              onChange={(e) => setTeams_name(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item sm={5}>
            <TextField
              fullWidth
              required
              variant="outlined"
              label="Teams el. paštas"
              id="teams_email"
              value={teams_email}
              onChange={(e) => setTeams_email(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item sm={5} id="grid-selector">
            <TextField
              fullWidth
              required
              variant="outlined"
              label="Valandos per savaitę"
              id="workHoursPerWeek"
              value={workHoursPerWeek}
              onChange={(e) => setWorkHoursPerWeek(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item sm={5}>
            <TextField
              fullWidth
              required
              variant="outlined"
              label="Galima pamaina"
              id=""
              value={teams_email}
              onChange={(e) => setTeams_email(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item sm={12}>
            <Stack direction="row" spacing={2}>
              <Button variant="contained" onClick={createTeacher}>
                Sukurti
              </Button>
              <Button variant="contained" onClick={() => navigate(-1)}>
                Grįžti
              </Button>
            </Stack>
          </Grid>
        </Grid>
      </form>
    </Container>
  );
}
