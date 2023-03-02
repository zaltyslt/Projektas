import { Button, Grid, Stack } from "@mui/material";
import { Container } from "@mui/system";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

export function ViewGroup() {
  const [group, setGroup] = useState({});
  const params = useParams();

  useEffect(() => {
    fetch("api/v1/group/view-group/" + params.id)
      .then((response) => response.json())
      .then(setGroup);
  }, []);

  return (
    <div>
      <Container>
        <Grid container rowSpacing={4}>
          <Grid item sm={10}>
            <header>
              <h1>Grupė: {group.name}</h1>
              <span id="modified-date">
                Paskutinį kartą redaguota: {group.modifiedDate}
              </span>
            </header>
          </Grid>

          <Grid item sm={12}>
            <h4>Programa</h4>
            <p>{group.program.programName}</p>
          </Grid>
          <Grid item sm={12}>
            <h4>Mokslo metai</h4>
            <p>{group.schoolYear}</p>
          </Grid>
          <Grid item sm={12}>
            <h4>Pamaina</h4>
            <p>{group.shift.name}</p>
          </Grid>
          <Grid item sm={12}>
            <h4>Studentų kiekis</h4>
            <p>{group.studentAmount}</p>
          </Grid>

          <Grid item sm={12}>
            <Stack direction="row" spacing={2}>
              <Link to={"/groups/edit/" + group.id}>
                <Button variant="contained">Redaguoti</Button>
              </Link>

              <Link to="/groups">
                <Button variant="contained">Grįžti</Button>
              </Link>
            </Stack>
          </Grid>
        </Grid>
      </Container>
    </div>
  );
}
