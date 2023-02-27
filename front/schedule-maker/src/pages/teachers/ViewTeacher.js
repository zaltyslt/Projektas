import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import { Button, Grid, Stack } from "@mui/material";

import { Container } from "@mui/system";

export function ViewTeacher() {
  const [teacher, setTeacher] = useState([]);
  const params = useParams();
  let navigate = useNavigate();

  useEffect(() => {
    fetch(`/api/v1/teachers/view?tid=${params.id}`)
      .then((response) => response.json())
      .then((data) => {
        setTeacher(data);
      });
  }, []);

  return (
    <div>
      <Container maxWidth="lg">
        <Grid container rowSpacing={4}>
          <Grid item sm={10}>
            <header>
              <h1>{teacher.fName}</h1>
              <span id="modified-date">
                Paskutinį kartą redaguota: {teacher.modifiedDateAndTime}
              </span>
            </header>
          </Grid>

          <Grid item sm={6}>
            <h4>Mokytojo vardas</h4>
            <p>{teacher.fName}</p>
          </Grid>
          <Grid item sm={6}>
            <h4>Mokytojo pavardė</h4>
            <p>{teacher.lName}</p>
          </Grid>

          <Grid item sm={6}>
            <h4>Nickname</h4>
            <p>{teacher.nickName}</p>
          </Grid>
          <Grid item sm={6}>
            <h4>Būsena</h4>
            <p>{teacher.isActive}</p>
          </Grid>

          <Grid item sm={6}>
            <h4>Kontaktinis telefonas</h4>
            {/* <p>{teacher.contacts.phone_number}</p> */}
          </Grid>
          <Grid item sm={6}>
            <h4>El. paštas</h4>
            {/* <p>{teacher.contacts.direct_email}</p> */}
          </Grid>

          <Grid item sm={6}>
            <h4>Teams vardas</h4>
            {/* <p>{teacher.contacts.teams_name}</p> */}
          </Grid>
          <Grid item sm={6}>
            <h4>Teams el. paštas</h4>
            {/* <p>{teacher.contacts.teams_email}</p> */}
          </Grid>

          <Grid item sm={6}>
            <h4>Valandos per savaitę</h4>
            {/* <p>{teacher.workHoursPerWeek}</p> */}
          </Grid>
          <Grid item sm={6}>
            <h4>Galima pamaina</h4>
            <p></p>
          </Grid>

          <Grid item sm={12}>
            <h4>Dėstomi dalykai</h4>
            <p></p>
          </Grid>

          <Grid item sm={12}>
            <Stack direction="row" spacing={2}>
              <Link to={`/update/${teacher.id}`}>
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
