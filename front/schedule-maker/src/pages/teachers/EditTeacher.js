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
import { useNavigate, useParams, useHref } from "react-router-dom";
import { width } from "@mui/system";

export function EditTeacher() {
  const [teacher, setTeacher] = useState("");
  const params = useParams();
  const [id, setId] = useState("");
  const [fName, setFName] = useState("");
  const [lName, setLName] = useState("");
  const [nickName, setNickName] = useState("");
  const [dateModified, setDateModified] = useState("");

  const [workHours, setWorkHours] = useState("");
  const [active, setActive] = useState(true);

  const [phone_number, setPhone_number] = useState("");
  const [direct_email, setDirect_email] = useState("");
  const [teams_name, setTeams_name] = useState("");
  const [teams_email, setTeams_email] = useState("");
  const [contacts, setContacts] = useState("");

  const [shifts, setShifts] = useState([]);
  const [selectedShift, setSelectedShift] = useState("");

  const [subject, setSubject] = useState(""); //tas, kur keliauja tarp array
  const [subjects, setSubjects] = useState([]); //visi subjectai
  const [chosenSubjects, setChosenSubjects] = useState([]); // pasirinkti dalykai
  const [freeSubjects, setFreeSubjects] = useState([]); // pasirinkti dalykai
  const [showSubjSelect, setShowSubjSelect] = useState(false); // rodyt/nerodyt subjects pasirinkima

  const [success, setSuccess] = useState();
  const [error, setError] = useState();

  let navigate = useNavigate();
  const listUrl = useHref("/teachers");

  const clear = () => {
    setFName("");
    setLName("");
    setNickName("");
    setSelectedShift("");

    setPhone_number("");
    setDirect_email("");
    setTeams_email("");
    setTeams_name("");

    setWorkHours("");
    setActive("");
    setSubjects("");
  };

const getTeachers = async ()=>{
  fetch("api/v1/teachers/view?tid=" + params.id)
  .then((response) => response.json())
  .then((data) => {
    setTeacher(data);
    setUpTeacher(data);
    return data
  } )
  .then((data) => {
    // console.log(teacher);
  });
}

  useEffect(() => {
   getTeachers();
  }, []);

  useEffect(() => {
    fetch("api/v1/teachers/subjects")
      .then((response) => response.json())
      .then((data) => {
        setSubjects(data);
        setFreeSubjects(data);
      });
  }, []);

  //Shifts
  useEffect(() => {
    fetch("api/v1/shift/get-active")
      .then((response) => response.json())
      .then(setShifts);
  }, []);

  const setUpTeacher = (data) => {
    setId(data.id)
    setFName(data.fName);
    setLName(data.lName);
    setNickName(data.nickName);
    setDateModified(data.dateModified);
    setPhone_number(data.contacts.phoneNumber);
    setDirect_email(data.contacts.directEmail);
    setTeams_name(data.contacts.teamsName);
    setTeams_email(data.contacts.teamsEmail);
    setWorkHours(data.workHoursPerWeek);
    setSelectedShift(data.teacherShiftDto);
    setActive(data.active);
    setChosenSubjects(data.subjectsList);
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

 useEffect(() => {
       
    const tempSubjects = freeSubjects.filter(
      (subA) => !chosenSubjects.map((subC) => subC.subjectId).includes(subA.subjectId)
    );
    setSubjects(tempSubjects);
  }, [showSubjSelect]);


  const handleShowSubjects = () => {
    setShowSubjSelect(!showSubjSelect);
  };

  const handleAddChosen = (subjectNew) => {
    // setSubject(subjectNew);

    const temp = [...chosenSubjects, subjectNew];
    setChosenSubjects(temp);
    const removed = subjects.filter((subject) => subject.subjectId != subjectNew.subjectId);
    setFreeSubjects(removed);
    setShowSubjSelect(false);
  };

  const handleRemoveChosen = (subjectRem) => {
    
    //patikrinti ar subjects neturi tokio dalyko
    console.log(freeSubjects);
    const moved = freeSubjects.filter( (subject) => subject.subjectId != subjectRem.subjectId
    );
    
    const removed = chosenSubjects.filter(
      (subject) => subject.subjectId != subjectRem.subjectId );
    setFreeSubjects([...moved, subjectRem]);
    setChosenSubjects(removed);
  };

  // Update teacher routine
  const createTeacher = async () => {
    const teacherContacts = {
      phoneNumber: phone_number,
      directEmail: direct_email,
      teamsEmail: teams_name,
      teamsName: teams_email,
    };

    const teacherShiftDto = {
      id: selectedShift.id,
      name: selectedShift.name,
    };

    // console.log(chosenSubjects);

    const chosenSubjectsDto = chosenSubjects.map(({ id }) => ({
      subjectId: id,
    }));
    const subjectIds = chosenSubjectsDto.map((obj) => obj.subjectId);

    // fetch("/api/v1/teachers", {
    fetch("/api/v1/teachers/create", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        id: null,
        fName: fName,
        lName: lName,
        nickName: nickName,
        workHoursPerWeek: workHours,
        active: active,
        contacts: teacherContacts,
        teacherShiftDto: teacherShiftDto,
        subjectsDtoList: chosenSubjectsDto,
      }),
    }).then(applyResult);
  };



  const deleteTeacher = async () => {
    console.log(teacher.id);
    await fetch(`/api/v1/teachers/active?tid=${teacher.id}&active=false`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
    }).then(() => (window.location = listUrl));
  };


  const row1 = 3.5;
  const row2 = 3.5;
  const row3 = 3.5;

  return (
    <Container style={{ maxWidth: "75rem" }}>
      <form>
        <h3 className="create-header">Redagavimas</h3>
        <h3>{fName + " " + lName}</h3>
        <span id="modified-date">
          Paskutinį kartą redaguota: {dateModified}
        </span>
        {<p className="Deleted">Error: {error && error} </p>}
        {<p className="Deleted">Success: {success && success} </p>}
        { <p className="Deleted">ID: {teacher.id && teacher.id} </p>}

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

          {/* <Grid item sm={row1}>
            <TextField
              fullWidth
              variant="outlined"
              label="Žyma"
              id="nickName"
              value={nickName}
              onChange={(e) => setNickName(e.target.value)}
            ></TextField>
          </Grid> */}

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
            <FormControl required fullWidth>
              <InputLabel id="shift-label">Galima pamaina</InputLabel>
              <Select
                label="Galima pamaina"
                labelId="shift-label"
                id="shift"
                // displayEmpty={false}
                // defaultValue={''}
                //defaultValue={`Pirma`}
                // value={selectedShift.name} //33370
                value={''}
                onChange={(e) => {
                  setSelectedShift(e.target.value);
                }}
              >
                {shifts.map((shift, index) => (
                  <MenuItem key={index} value={shift.name}>
                    {shift.name}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid>

          {/* <Grid item sm={row3}>
            <FormControl fullWidth>
              <InputLabel id="active-select-label">Būsena</InputLabel>
              <Select
                label="isActive"
                id="active"
                fullWidth
                required
                defaultValue={true}
                defaultOpen={false}
                variant="outlined"
                displayEmpty
                inputProps={{ "aria-label": "Without label" }}
                onChange={(e) => setActive(e.target.value)}
              >
                {isActive.map((isActive, index) => (
                  <MenuItem key={index} value={isActive.value}>
                    {isActive.label}
                  </MenuItem>
                ))}
              </Select>
            </FormControl>
          </Grid> */}

          <Grid item sm={12}>
            {showSubjSelect && (
              <FormControl fullWidth>
                <InputLabel id="subjects-label">Dalykai</InputLabel>
                <Select
                  label="Dalyko pavadinimas"
                  labelId="subject-label"
                  id="subject"
                  value={subject}
                  defaultOpen={true}
                  onChange={(e) => handleAddChosen(e.target.value)}
                >
                  {subjects.map((subject, index) => (
                    <MenuItem key={index} value={subject}>
                      {subject.name}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            )}

            <TableContainer component={Paper}>
              <Table aria-label="custom pagination table">
                <TableHead>
                  <TableRow>
                    <TableCell>Dėstomi dalykai</TableCell>
                    <TableCell>Moduliai </TableCell>
                    <TableCell>
                      <Button variant="contained" onClick={handleShowSubjects}>
                        Pridėti
                      </Button>
                    </TableCell>
                  </TableRow>
                </TableHead>

                <TableBody>
                  {chosenSubjects &&
                    chosenSubjects.map((subject, index) => (
                      <TableRow key={index}>
                        <TableCell component="th" scope="row">
                          {subject.name}
                        </TableCell>
                        <TableCell>
                          {subject.module && subject.module}
                        </TableCell>
                        <TableCell align="center" className="activity">
                          <Button
                            variant="contained"
                            onClick={(e) => handleRemoveChosen(subject)}
                          >
                            Ištrinti
                          </Button>
                        </TableCell>
                      </TableRow>
                    ))}
                </TableBody>
              </Table>
            </TableContainer>
          </Grid>

          <Grid item sm={12}>
            <Stack direction="row" spacing={2}>
              <Button variant="contained" onClick={createTeacher}>
                Išsaugoti
              </Button>
              <Button variant="contained" onClick={deleteTeacher}>
                Ištrinti
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
