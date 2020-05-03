cd ../../bounswe2020group2.wiki &&
git pull &&
cp ./Milestone-1-Report.md ../bounswe2020group2/milestone1/report.md &&
cd ../bounswe2020group2/milestone1 &&
rm ./images/* &&
python ./download_images.py ./report.md ./report.mod.md &&
pandoc report.mod.md -f gfm  --standalone --toc --toc-depth=3 -N -o report.tex