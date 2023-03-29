import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import { Alert, Button, Grid, Stack } from "@mui/material";

import { Container } from "@mui/system";

export function ViewTeacher() {
  const [teacher, setTeacher] = useState([]);
  const [contacts, setContacts] = useState([]);
  const [message, setMessage] = useState([]);
  const [teacherSubjects, setTeacherSubjects] = useState([]);
  const [foundSubjects, setFoundSubjects] = useState([]);
  const params = useParams();
  let navigate = useNavigate();

  const getTeacherData = async () => {
    fetch(`api/v1/teachers/view?tid=${params.id}`)
      .then((response) => response.json())
      .then((data) => {
        setTeacher(data);
        setContacts(data.contacts);
        setTeacherSubjects(data.subjectsList);
      });
  };

  useEffect(() => {
    getTeacherData();
  }, []);

  return (
    <div>
      <Container maxWidth="lg">
        <Grid container rowSpacing={3}>
          <Grid item sm={10} marginTop={4}>
            <header>
              <h1>{teacher.fName + " " + teacher.lName}</h1>
              <span id="modified-date">
                Paskutinį kartą redaguota: {teacher.dateModified}
              </span>
            </header>
          </Grid>
          <Grid item sm={12}>
            <h4>Kontaktinis telefonas</h4>
            <p>{contacts.phoneNumber ? contacts.phoneNumber : ""}</p>
          </Grid>
          <Grid item sm={12}>
            <h4>El. paštas</h4>
            <p>{contacts.directEmail ? contacts.directEmail : ""}</p>
          </Grid>
          <Grid item sm={12}>
            <h4>Teams vardas</h4>
            <p>{contacts.teamsName ? contacts.teamsName : ""}</p>
          </Grid>
          <Grid item sm={12}>
            <h4>Teams el. paštas</h4>
            <p>{contacts.teamsEmail ? contacts.teamsEmail : ""}</p>
          </Grid>
          <Grid item sm={12}>
            <h4>Valandos per savaitę</h4>
            <p>{teacher.workHoursPerWeek && teacher.workHoursPerWeek}</p>
          </Grid>
          <Grid item sm={12}>
            <h4>Galima pamaina</h4>
            <p>
              {teacher.selectedShift
                ? teacher.selectedShift.name
                : "Duomenys nepasiekiami"}
            </p>
          </Grid>
          <Grid item sm={12}>
            <h4>Dėstomi dalykai</h4>
            {teacherSubjects.map((subjectItem, index) => {
              return <p key={index}>{subjectItem.name}</p>;
            })}
          </Grid>
          <Grid item sm={12} marginBottom={10}>
            <Stack direction="row" spacing={2}>
              <Link to={`/teachers/edit/${teacher.id}`}>
                <Button variant="contained">Redaguoti</Button>
              </Link>
              <Button variant="contained" onClick={() => navigate(-1)}>
                Grįžti
              </Button>
            </Stack>
          </Grid>
        </Grid>
      </Container>
    </div>
  );
}
