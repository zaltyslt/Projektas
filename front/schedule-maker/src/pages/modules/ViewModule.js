import { Button, Grid } from "@mui/material";
import { Container, Stack } from "@mui/system";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

export function ViewModule() {
  const [module, setModule] = useState({});
  const params = useParams();

  useEffect(() => {
    fetch("api/v1/modules/" + params.id)
      .then((response) => response.json())
      .then((data) => {
        setModule(data);
      });
  }, []);

  return (
    <div>
      <Container>
        <Grid container rowSpacing={4}>
          <Grid item sm={10}>
            <header>
              <h1>{module.name}</h1>
              <span id="modified-date">Paskutinį kartą redaguota: {module.modifiedDate}</span>
            </header>
          </Grid>

          <Grid item sm={12}>
            <h4>Modulio kodas</h4>
            <p>{module.number}</p>
          </Grid>

          <Grid item sm={12}>
            <h4>Modulio pavadinimas</h4>
            <p>{module.name}</p>
          </Grid>

          <Grid item sm={12}>
            <Stack direction="row" spacing={2}>
              <Link to={"/modules/edit/" + module.id}>
                <Button variant="contained">Redaguoti</Button>
              </Link>

              <Link to="/modules">
                <Button variant="contained">Grįžti</Button>
              </Link>
            </Stack>
          </Grid>
          
        </Grid>
      </Container>
    </div>
  );
}