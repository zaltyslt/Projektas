import { Button, Grid, Typography } from "@mui/material";
import { Container, Stack } from "@mui/system";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import "./Subject.css";

export function ViewSubject() {
  const [subject, setSubject] = useState({});
  const params = useParams();

  useEffect(() => {
    fetch("api/v1/subjects/" + params.id)
      .then((response) => response.json())
      .then((data) => {
        setSubject(data);
      });
  }, []);

  return (
    <div>
      <Container>
        <Grid container rowSpacing={4}>
          <Grid item sm={10} marginTop={4}>
            <header>
              <h1>{subject.name}</h1>
              <span id="modified-date">
                Paskutinį kartą redaguota: {subject.modifiedDate}
              </span>
            </header>
          </Grid>
          <Grid item sm={12}>
            <h4>Modulis</h4>
            {subject.module ? (
              subject.module.deleted ? (
                <p className="Deleted">
                  {subject.module.name} - modulis buvo ištrintas.
                </p>
              ) : (
                subject.module.name
              )
            ) : (
              <p>Nenurodytas</p>
            )}
          </Grid>
          <Grid item sm={12}>
            <h4>Aprašymas</h4>
            <p>{subject.description}</p>
          </Grid>
          <Grid item sm={12}>
            <h4>Tinkamos klasės</h4>
            {subject.classRooms &&
              subject.classRooms.map((classRoom) => (
                <p
                  key={classRoom.id}
                  className={classRoom.active ? "" : "Deleted"}
                >
                  {classRoom.classroomName}
                </p>
              ))}
          </Grid>
          <Grid item sm={12}>
            <Stack direction="row" spacing={2}>
              <Link to={"/subjects/edit/" + subject.id}>
                <Button id="edit-button-view-subject" variant="contained">
                  Redaguoti
                </Button>
              </Link>
              <Link to="/subjects">
                <Button id="back-button-view-subject" variant="contained">
                  Grįžti
                </Button>
              </Link>
            </Stack>
          </Grid>
        </Grid>
      </Container>
    </div>
  );
}
