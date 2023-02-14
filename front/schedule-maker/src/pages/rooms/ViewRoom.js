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

export function ViewRoom() {
  const [classroom, setClassroom] = useState({});
  const params = useParams();
  let navigate = useNavigate();

  useEffect(() => {
    fetch(`/api/v1/classrooms/classroom/${params.id}`)
      .then((response) => response.json())
      .then(setClassroom);
  }, [params.id]);

  return (
    <div>
      <Container>
        <Grid container rowSpacing={4}>
          <Grid item lg={10}>
            <header>
              <h1>{classroom.classroomName}</h1>
              <h5>Įrašo sukūrimo data: {classroom.createdDate}</h5>
              <h5>Paskutinį kartą redaguota: {classroom.modifiedDate}</h5>
            </header>
          </Grid>

          <Grid item lg={12}>
            <h4>Pastatas</h4>
            {classroom.building}
          </Grid>

          <Grid item lg={12}>
            <h4>Klasės aprašas</h4>
            <p>{classroom.description}</p>
          </Grid>

          <Grid item lg={12}>
            <Stack direction="row" spacing={2}>
              <Link to={`/update/${classroom.id}`}>
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
