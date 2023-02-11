import {
    Button,
    Grid,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableFooter,
    TableHead,
    TablePagination,
    TableRow,
  } from "@mui/material";
  import { Container } from "@mui/system";
  
  import { useEffect, useState } from "react";
  import { Link } from "react-router-dom";
  
  export function ModuleList() {
    const [modules, setModules] = useState([]);
  
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);
  
    useEffect(() => {
      fetch("api/v1/modules")
        .then((response) => response.json())
        .then(setModules);
    }, []);
  
    const emptyRows =
      page > 0 ? Math.max(0, (1 + page) * rowsPerPage - modules.length) : 0;
  
    const handleChangePage = (event, newPage) => {
      setPage(newPage);
    };
  
    const handleChangeRowsPerPage = (event) => {
      setRowsPerPage(parseInt(event.target.value, 10));
      setPage(0);
    };
  
    return (
      <div>
        <Container maxWidth="lg">
          <Grid container rowSpacing={3}>
            <Grid item sm={12}>
              <h3>Modulių sąrašas</h3>
            </Grid>
            <Grid item sm={4}>
              <Link to="/modules/create">
                <Button variant="contained">Pridėti naują</Button>
              </Link>
            </Grid>
          </Grid>
  
          <TableContainer component={Paper}>
            <Table aria-label="custom pagination table">
              <TableHead>
                <TableRow>
                  <TableCell>Modulio kodas</TableCell>
                  <TableCell>Modulio pavadinimas</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {(rowsPerPage > 0
                  ? modules.slice(
                      page * rowsPerPage,
                      page * rowsPerPage + rowsPerPage
                    )
                  : modules
                ).map((module) => (
                  <TableRow key={module.id}>
                    <TableCell component="th" scope="row">
                      <Link to={"/modules/view/" + module.id}>
                       {module.name}
                      </Link>
                     
                    </TableCell>
                    <TableCell>{module.description}</TableCell>
                  </TableRow>
                ))}
  
                {emptyRows > 0 && (
                  <TableRow style={{ height: 53 * emptyRows }}>
                    <TableCell colSpan={6} />
                  </TableRow>
                )}
              </TableBody>
              <TableFooter>
                <TableRow>
                  <TablePagination
                    labelRowsPerPage="Rodyti po"
                    rowsPerPageOptions={[10, 20, { label: "Visi", value: -1 }]}
                    colSpan={3}
                    count={modules.length}
                    page={page}
                    SelectProps={{
                      inputProps: {
                        "aria-label": "Rodyti po",
                      },
                      native: true,
                    }}
                    onPageChange={handleChangePage}
                    rowsPerPage={rowsPerPage}
                    onRowsPerPageChange={handleChangeRowsPerPage}
                  ></TablePagination>
                </TableRow>
              </TableFooter>
            </Table>
          </TableContainer>
        </Container>
      </div>
    );
  }