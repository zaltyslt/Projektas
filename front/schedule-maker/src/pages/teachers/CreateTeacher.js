import {
  Alert,
  Button,
  Container,
  FormControl,
  InputLabel,
  Grid,
  MenuItem,
  Paper,
  Select,
  Stack,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TextField,
} from "@mui/material";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import ".././pages.css";
import "../teachers/Teacher.css";
import { useNavigate } from "react-router-dom";
import { width } from "@mui/system";

export function CreateTeacher() {
  const [subjects, setSubjects] = useState([]);
  const [showSubjSelect, setShowSubjSelect] = useState(false);
  
  const [subject, setSubject] = useState("");
  const [chosenSubjects, setChosenSubjects] = useState([]);

  const [fName, setFName] = useState("");
  const [lName, setLName] = useState("");
  const [nickName, setNickName] = useState("");

  const [phone_number, setPhone_number] = useState("");
  const [direct_email, setDirect_email] = useState("");
  const [teams_name, setTeams_name] = useState("");
  const [teams_email, setTeams_email] = useState("");

  const [workHours, setWorkHours] = useState("");
  const [active, setActive] = useState("");

  let navigate = useNavigate();

  const clear = () => {
    setFName("");
    setLName("");
    setNickName("");

    setPhone_number("");
    setDirect_email("");
    setTeams_email("");
    setTeams_name("");

    setWorkHours("");
    setActive("");
    setSubjects("");
  };

  useEffect(() => {
    fetchSubjects();
  }, []);

  const fetchSubjects = () => {
    fetch("api/v1/subjects")
      .then((response) => response.json())
      .then((data) => {
        setSubjects(data);
        return data;
      })
      // .then((data) => setFilteredSubjects(data))
      .then((data) => console.log(data));
  };

  const isActive = [
    { value: "true", label: "Aktyvus" },
    { value: "false", label: "Neaktyvus" },
  ];

  const applyResult = (result) => {
    if (result.ok) {
      setSuccess("Sėkmingai pridėta!");
      clear();
    } else {
      setError("Nepavyko sukurti!");
    }
  };
  const handleShowSubjects  = () => {
    setShowSubjSelect (!showSubjSelect);
  };

  const handleChosenSubjects  = (subjectNew) => {
    // console.log(event.name +" " +event.id);
    const sub =  chosenSubjects.push(subjectNew);
    console.log(chosenSubjects);

    setShowSubjSelect(false);

// Find the index of the object with id = 3
   const index = subjects.findIndex(subjectNew => subjectNew.id);

// Remove the object at the specified index
subjects.splice(index, 1);


  // const subjects = [...chosenSubjects, event];
  //   setChosenSubjects (subject);
  };
  // const handleRoomInput = (event) => {
  //   const {
  //     target: { value },  } = event;
  //   setClassRooms(typeof value === "string" ? value.split(",") : value);
  // };


  const createTeacher = async () => {
    fetch("/api/v1/teachers", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        fName,
        lName,
        nickName,
      }),
    }).then(applyResult);
  };
  const row1 = 3.5;
  const row2 = 3.5;
  const row3 = 3.5;
  return (
    <Container style={{ maxWidth: "75rem" }}>
      <form>
        <h3 className="create-header">Pridėti naują dėstytoją</h3>
        <Grid
          container
          direction="row"
          justifyContent="space-between"
          alignItems="center"
          rowSpacing={3}
        >
          <Grid item sm={row1}>
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

          <Grid item sm={row1}>
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

          <Grid item sm={row1}>
            <TextField
              fullWidth
              variant="outlined"
              label="Žyma"
              id="nickName"
              value={nickName}
              onChange={(e) => setNickName(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item sm={row2}>
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

          <Grid item sm={row2}>
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

          <Grid item sm={row2}>
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

          <Grid item sm={row2}>
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

          <Grid item sm={row3}>
            <TextField
              fullWidth
              required
              variant="outlined"
              label="Valandos per savaitę"
              id="workHours"
              value={workHours}
              onChange={(e) => setWorkHours(e.target.value)}
            ></TextField>
          </Grid>

          <Grid item sm={row3}>
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
          <Grid item sm={row3}>
            <FormControl fullWidth>
              <InputLabel id="demo-simple-select-label">Būsena</InputLabel>
              <Select
                label="isActive"
                id="active"
                fullWidth
                required
                multiline
                value={active}
                variant="outlined"
                displayEmpty
                inputProps={{ "aria-label": "Without label" }}
                onChange={(e) => setActive(e.target.value)}
              >
                {isActive.map((isActive) => (
                  <MenuItem key={isActive.value} value={isActive.value}>
                    {isActive.label}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>

         <Grid item sm={12} >
         { showSubjSelect && (<FormControl fullWidth>
              <InputLabel id="subjects-label">Dalykai</InputLabel>
              <Select
                label="Dalyko pavadinimas"
                labelId="subject-label"
                id="subject"
                value={subject}
                onChange={(e) => handleChosenSubjects(e.target.value)}
              >
                {subjects.map((subject) => (
                  <MenuItem key={subject.id} value={subject}>
                    {subject.name}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>)
          }
           
            <TableContainer component={Paper}>
              <Table aria-label="custom pagination table">
                <TableHead>
                  <TableRow>
                    <TableCell>Dėstomi dalykai</TableCell>
                    <TableCell></TableCell>
                    <TableCell>
                    <Button variant="contained" onClick={handleShowSubjects}>
                        Pridėti
                      </Button>
                    </TableCell>
                  </TableRow>
                </TableHead>

                <TableBody>
                  {/* <TableRow>
                    <TableCell component="th" scope="row">
                      sssss
                    </TableCell>
                    <TableCell>
                    ffffff
                    </TableCell>
                    <TableCell>
                      <Button variant="contained" onClick={createTeacher}>
                        Išmesti
                      </Button>
                    </TableCell>
                  </TableRow> */}

                 { 
                  
                  (chosenSubjects).map((subject) => (
                  <TableRow key={subject.id}>
                    <TableCell component="th" scope="row">
                      {subject.name}
                    </TableCell>
                    <TableCell>
                      {subject.module ? (
                        subject.module.deleted ? (
                          <p className="Deleted">
                            {subject.module.name} - modulis buvo ištrintas
                          </p>
                        ) : (
                          subject.module.name
                        )
                      ) : (
                        <p>Nenurodytas</p>
                      )}
                    </TableCell>
                    <TableCell align="center" className="activity">
                      <Button
                        variant="contained"
                        onClick={() => handleRestore(subject.id)}
                      >
                        Atstatyti
                      </Button>
                    </TableCell>
                  </TableRow>
                ))}
                
                 }
                </TableBody>
              </Table>
            </TableContainer>
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
