all:
	for number in doc_*.tex ; do \
		pdflatex -interaction=nonstopmode $$number ; \
	done
clean:
	rm -f *.pdf
	rm -f *.nav
	rm -f *.aux
	rm -f *.synctex.gz
	rm -f *.out
	rm -f *.log
	rm -f *.tog
	rm -f *.snm

