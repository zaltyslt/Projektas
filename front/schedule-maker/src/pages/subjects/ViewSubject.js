import { Button, Paper, Grid } from "@mui/material";
import { Container, Stack } from "@mui/system";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

export function ViewSubject() {
  const [subject, setSubject] = useState({});
  const params = useParams();

  useEffect(() => {
    fetch("api/v1/subjects/" + params.id)
      .then((response) => response.json())
      .then(setSubject);
  }, []);

  return (
    <div>
      <Container>
        <Grid container rowSpacing={4}>
          <Grid item lg={10}>
            <header>
              <h1>{subject.name}</h1>
              <h5>Paskutinį kartą redaguota: {subject.modifiedDate}</h5>
            </header>
          </Grid>

          <Grid item lg={12}>
            <h4>Modulis</h4>
            <p></p>
          </Grid>

          <Grid item lg={12}>
            <h4>Aprašymas</h4>
            <p>{subject.description}</p>
          </Grid>

          <Grid item lg={12}>
            <h4>Pageidaujamos klasės</h4>
            <p></p>
          </Grid>

          <Grid item lg={12}>
            <Stack direction="row" spacing={2}>
              <Link to={"/subjects/edit/" + subject.id}>
                <Button variant="contained">Redaguoti</Button>
              </Link>

              <Link to="/subjects">
                <Button variant="contained">Grįžti</Button>
              </Link>
            </Stack>
          </Grid>
          
        </Grid>
      </Container>
    </div>
  );
}
