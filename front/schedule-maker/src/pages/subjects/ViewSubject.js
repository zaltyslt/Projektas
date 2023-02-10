import { Button, Paper } from "@mui/material";
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
        <Paper variant="outlined">
          <article>
            <header>
              <h2>{subject.name}</h2>
            </header>
            <section>
              <h4>Modulis</h4>
            </section>
            <section>
              <h4>Aprašymas</h4>
              <p>{subject.description}</p>
            </section>
            <section>
                <h4>Pageidaujamos klasės</h4>
            </section>
          </article>
        </Paper>
        <Stack direction="row" spacing={2}>
           <Link to={"/subjects/edit/" + subject.id}>
           <Button variant="contained">
            Redaguoti
          </Button>
           </Link> 
          
          <Link to="/subjects">
            <Button variant="contained">Grįžti</Button>
          </Link>
        </Stack>
      </Container>
    </div>
  );
}
