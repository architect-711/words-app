import { BrowserRouter, Route, Routes } from "react-router-dom";
import Head from "./components/shared/head/Head"
import HomePage from "./pages/HomePage";
import WordPage from "./pages/WordPage";
import WordsPage from "./pages/WordsPage";
import NewWordPage from "./pages/NewWordPage";

export default function App() {
	return (
		<BrowserRouter>
			<Head/>	
			<Routes>

				<Route path="" element={<HomePage/>}/>
				<Route path="/words" element={<WordsPage/>}/>
				<Route path="/words/:id" element={<WordPage/>}/>
				<Route path="/words/new" element={<NewWordPage/>}/>

			</Routes>
		</BrowserRouter>
	);
}
