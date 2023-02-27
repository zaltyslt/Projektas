import { useEffect, useState, React } from "react";
import { Link } from "react-router-dom";
import {
    Button,
    Checkbox,
    FormControlLabel,
    FormGroup,
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
    TextField,
    FormControl,
    InputLabel,
    Select,
    MenuItem,
    Stack
} from "@mui/material";
import { Container } from "@mui/system";
import { SelectChangeEvent } from "@mui/material/Select";


export function ProgramList() {
    const [programs, setPrograms] = useState([])
    const [filter, setFilter] = useState("")
    const [currentPage, setCurrentPage] = useState(1)
    const [currentPage2, setCurrentPage2] = useState(1)
    const [programsPerPage, setProgramsPerPage] = useState(10)
    const [programsPerPage2, setProgramsPerPage2] = useState(10)
    const paginate = (pageNumber) => setCurrentPage(pageNumber)
    const paginate2 = (pageNumber2) => setCurrentPage2(pageNumber2)
    const [isChecked, setChecked] = useState(false)


    const fetchPrograms = () => {
        fetch("/api/v1/programs")
            .then((responce) => responce.json())
            .then((jsonResponce) => setPrograms(jsonResponce));
    };

    const enableProgram = (event, program) => {
        console.log(program)
        fetch(`/api/v1/programs/enable-program/${program.id}`, {
            method: "PATCH",
            headers: {
                "Content-Type": "application/json",
            }
        }).then(fetchPrograms);
    };

    useEffect(() => {
        fetchPrograms();
    }, []);

    const filteredPrograms = programs.filter((program) => {
        return String(program.programName)
            .toLowerCase()
            .includes(filter.toLowerCase())
            && program.active === true;
    });

    const filteredDisabledPrograms = programs.filter((program) => {
        return String(program.programName)
            .toLowerCase()
            .includes(filter.toLowerCase())
            && program.active === false;
    });


    const indexOfLastProgram = currentPage * programsPerPage;
    const indexOfFirstProgram = indexOfLastProgram - programsPerPage;
    const currentPrograms = filteredPrograms.slice(
        indexOfFirstProgram,
        indexOfLastProgram
    );

    const pageNumbers = [];
    for (
        let i = 1;
        i <= Math.ceil(filteredPrograms.length / programsPerPage);
        i++
    ) {
        pageNumbers.push(i);
    }

    const indexOfLastProgram2 = currentPage2 * programsPerPage2;
    const indexOfFirstProgram2 = indexOfLastProgram2 - programsPerPage2;
    const currentPrograms2 = filteredDisabledPrograms.slice(
        indexOfFirstProgram2,
        indexOfLastProgram2
    );

    const pageNumbers2 = [];
    for (
        let i = 1;
        i <= Math.ceil(filteredDisabledPrograms.length / programsPerPage2);
        i++
    ) {
        pageNumbers2.push(i);
    }

    const handlePageChange = (
        event: React.MouseEvent<HTMLButtonElement> | null,
        newPage: number
    ) => {
        setCurrentPage(newPage);
    };
    const handleRowsPerPageChange = (
        event: React.ChangeEvent<HTMLInputElement>
    ) => {
        setProgramsPerPage(Number(event.target.value));
        setCurrentPage(1);
    };

    return (
        <div>
            <Container maxWidth="lg">
                <Grid container rowSpacing={3}>
                    <Grid item sm={10}>
                        <h3>Programų sąrašas</h3>
                    </Grid>
                    <Grid item sm={2}>
                        <Stack direction="row" justifyContent="flex-end">
                            <Link to="/create-program">
                                <Button variant="contained">Pridėti naują</Button>
                            </Link>
                        </Stack>
                    </Grid>

                    <Grid item sm={12}>


                        <TextField
                            fullWidth
                            variant="outlined"
                            name="search-form"
                            label="Paieška"
                            id="search-form"
                            value={filter}
                            onChange={(e) => setFilter(e.target.value)}
                        ></TextField>

                    </Grid>
                </Grid>
                <TableContainer component={Paper}>
                    <Table aria-label="custom pagination table">
                        <TableHead>
                            <TableRow>
                                <TableCell>Programos pavadinimas</TableCell>
                                <TableCell className="empty-activity"></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {filteredPrograms
                                .slice(
                                    (currentPage - 1) * programsPerPage,
                                    currentPage * programsPerPage
                                )
                                .map((program) => (
                                    <TableRow key={program.id}>
                                        <TableCell component="th" scope="row">
                                            <Link to={`/programs/view-program/${program.id}`}>
                                                {program.programName}
                                            </Link>
                                        </TableCell>
                                        <TableCell></TableCell>
                                    </TableRow>
                                ))}
                        </TableBody>
                        <TableFooter>
                            <TableRow>
                                <TablePagination
                                    labelRowsPerPage="Rodyti po"
                                    labelDisplayedRows={({ from, to, count }) => `${from}-${to} iš ${count}`}
                                    rowsPerPageOptions={[10, 20, { label: "Visi", value: filteredPrograms.length }]}
                                    colSpan={2}
                                    count={filteredPrograms.length}
                                    page={currentPage - 1}
                                    rowsPerPage={programsPerPage}
                                    onPageChange={(_, page) => setCurrentPage(page + 1)}
                                    onRowsPerPageChange={(e) =>
                                        setProgramsPerPage(parseInt(e.target.value))
                                    }
                                />
                            </TableRow>
                        </TableFooter>
                    </Table>
                </TableContainer>
                <FormGroup>
                    <FormControlLabel
                        control={<Checkbox />}
                        label="Ištrintos programos"
                        onChange={(e) =>
                            e.target.checked ? setChecked(true) : setChecked(false)
                        }
                    />
                </FormGroup>
                {isChecked &&
                    <TableContainer component={Paper}>
                        <Table aria-label="custom pagination table">
                            <TableHead>
                                <TableRow>
                                    <TableCell>Klasės pavadinimas</TableCell>
                                    <TableCell className="activity"></TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {filteredDisabledPrograms
                                    .slice(
                                        (currentPage2 - 1) * programsPerPage2,
                                        currentPage2 * programsPerPage2
                                    )
                                    .map((program) => (
                                        <TableRow key={program.id}>
                                            <TableCell component="th" scope="row">
                                                {program.programName}
                                            </TableCell>
                                            <TableCell>
                                                <Button variant="contained"
                                                    data-value='true'
                                                    value={program}
                                                    onClick={(e) => { enableProgram(e, program); }}
                                                >Atstatyti
                                                </Button>
                                            </TableCell>
                                        </TableRow>
                                    ))}
                            </TableBody>
                            <TableFooter>
                                <TableRow>
                                    <TablePagination
                                        labelRowsPerPage="Rodyti po"
                                        rowsPerPageOptions={[10, 20, { label: "Visi", value: filteredDisabledPrograms.length }]}
                                        labelDisplayedRows={({ from, to, count }) => `${from}-${to} iš ${count}`}
                                        count={filteredDisabledPrograms.length}
                                        page={currentPage2 - 1}
                                        rowsPerPage={programsPerPage2}
                                        onPageChange={(_, page) => setCurrentPage2(page + 1)}
                                        onRowsPerPageChange={(e) =>
                                            setProgramsPerPage2(parseInt(e.target.value))
                                        }
                                    />
                                </TableRow>
                            </TableFooter>
                        </Table>
                    </TableContainer>
                }
            </Container>
        </div>
    );
}