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
  import { useNavigate, useParams } from "react-router-dom";
  import { width } from "@mui/system";
  
  export function EditTeacher() {
    const [teacher, setTeacher] =useState(''); 
    const params = useParams();
    
    const [fName, setFName] = useState("");
    const [lName, setLName] = useState("");
    const [nickName, setNickName] = useState("");
    
    const [workHours, setWorkHours] = useState("");
    const [active, setActive] = useState(true);
    
    const [phone_number, setPhone_number] = useState("");
    const [direct_email, setDirect_email] = useState("");
    const [teams_name, setTeams_name] = useState("");
    const [teams_email, setTeams_email] = useState("");
    const [contacts, setContacts] = useState("");
        
    const [shifts, setShifts] = useState([]);
    const [selectedShift, setSelectedShift] = useState("");
    
    
    const [subject, setSubject] = useState("");
    const [subjects, setSubjects] = useState([]);
    const [showSubjSelect, setShowSubjSelect] = useState(false);
    const [chosenSubjects, setChosenSubjects] = useState([]);

    const [success, setSuccess] = useState();
    const [error, setError] = useState();
  
    let navigate = useNavigate();
  
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
  
    useEffect(() => {
      fetchSubjects();
    }, []);

    useEffect(() => {
        fetch("api/v1/teachers/view?tid=" + params.id)
          .then((response) => response.json())
          .then(setTeacher)
          .then(()=>
            {
                console.log(params);
            console.log(teacher);
        });
      }, []);
  
    useEffect(() => {
      fetch("api/v1/shift/get-active")
        .then((response) => response.json())
        .then(setShifts);
    }, []);
  
    const fetchSubjects = () => {
      fetch("api/v1/subjects")
        .then((response) => response.json())
        .then((data) => {
          setSubjects(data);
          return data;
        })
        // .then((data) => setFilteredSubjects(data))
        // .then((data) => console.log(data));
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
    const handleShowSubjects = () => {
      setShowSubjSelect(!showSubjSelect);
    };
  
    const handleChosenSubjects = (subjectNew) => {
      setChosenSubjects([...chosenSubjects, subjectNew]);
      const removed = subjects.filter(subject => subject.id != subjectNew.id);
      setSubjects(removed);
      setShowSubjSelect(false);
    };
  const handleRemoveChosen = (subjectRem)=> {
    console.log(subjectRem);
    setSubjects([...subjects, subjectRem]);
    const removed = chosenSubjects.filter(subject => subject.id != subjectRem.id);
    setChosenSubjects(removed);
  };
  
    const createTeacher = async () => {
  
      const teacherContacts = {
        phoneNumber: phone_number,
        directEmail: direct_email,
        teamsEmail: teams_name,
        teamsName: teams_email
      };
  
      const teacherShiftDto = {
        "id":selectedShift.id,
        "name": selectedShift.name
      };
  
      console.log(chosenSubjects);
      
      const chosenSubjectsDto = chosenSubjects.map(({ id }) => ({ "subjectId": id }));
      const subjectIds = chosenSubjectsDto.map(obj => obj.subjectId);
          
      // fetch("/api/v1/teachers", {
      fetch("/api/v1/teachers/create", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          "id": null, 
          "fName": fName,
          "lName": lName,
          "nickName": nickName,
          "workHoursPerWeek": workHours,
          "active":  active,
          "contacts":  teacherContacts,
          "teacherShiftDto": teacherShiftDto, 
          "subjectsDtoList": chosenSubjectsDto
        
    
  
        }),
      }).then(applyResult)
      .then(console.log( active));
    };
    const row1 = 3.5;
    const row2 = 3.5;
    const row3 = 3.5;
    return (
      <Container style={{ maxWidth: "75rem" }}>
        <form>
          <h3 className="create-header">Redagavimas</h3>
          <h3>{teacher.fName + " " + teacher.lName}</h3>
         <span id="modified-date">Paskutinį kartą redaguota: {teacher.dateMotified}</span>
          {<p className="Deleted">Error:   {error&&error}   </p>}
          {<p className="Deleted">Success:   {success && success}   </p>}
  
          
          
          
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
                value={teacher.fName}
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
                value={teacher.lName}
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
              <FormControl required fullWidth>
                <InputLabel id="shift-label">Galima pamaina</InputLabel>
                <Select
                  label="Galima pamaina"
                  labelId="shift-label"
                  id="shift"
                  value={selectedShift}
                  onChange={(e) => {
                    setSelectedShift(e.target.value);
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
            <Grid item sm={row3}>
              <FormControl fullWidth>
                <InputLabel id="demo-simple-select-label">Būsena</InputLabel>
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
  
                 { isActive.map((isActive,index) => (
                    <MenuItem key={index} value={isActive.value}>
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
                  defaultOpen={true}
                  onChange={(e) => handleChosenSubjects(e.target.value)}
                >
                  {subjects.map((subject, index) => (
                    <MenuItem key={index} value={subject}>
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
                      <TableCell>Moduliai </TableCell>
                      <TableCell>
                        <Button variant="contained" onClick={handleShowSubjects}>
                          Pridėti
                        </Button>
                      </TableCell>
                    </TableRow>
                  </TableHead>
  
                  <TableBody>
  
  
                    {chosenSubjects.map((subject,index) => (
                      <TableRow key={index}>
                        <TableCell  component="th" scope="row">
                          {subject.name}
                        </TableCell>
                        <TableCell>
                          {subject.module
                          ? (
                            subject.module.deleted ? (
                              <p className="Deleted">
                                {subject.module.name} - modulis buvo ištrintas
                              </p>
                            ) : (
                              subject.module.name
                            )
                          )
                          : ( <p>Nenurodytas</p>
                          )}
                        </TableCell>
                        <TableCell  align="center" className="activity">
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
  