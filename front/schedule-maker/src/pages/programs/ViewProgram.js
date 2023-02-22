import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import {
  Button,
  Grid,
  Stack,
} from "@mui/material";
import { Container } from "@mui/system";


export function ViewProgram() {
  const [program, setProgram] = useState({});
  const params = useParams();
  let navigate = useNavigate();

  useEffect(() => {
    fetch(`/api/v1/programs/program/${params.id}`)
      .then((response) => response.json())
      .then(setProgram);
  }, [params.id]);


  return (
    <div>
      <Container>
        <Grid container rowSpacing={4}>
          <Grid item sm={10}>
            <header>
              <h1>{program.programName}</h1>
              <h5>Paskutinį kartą redaguota: {program.modifiedDate}</h5>
            </header>
          </Grid>
          <Grid item sm={12}>
            <h4>Programos aprašas</h4>
            <p>{program.description}</p>
          </Grid>
          <Grid item sm={12}>
            <Stack direction="row" spacing={2}>
              <Link to={`/update-program/${program.id}`}>
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
