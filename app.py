import itertools
import logging
import time
import pyautogui
from numpy.random import normal
from utils import getArgs

logging.basicConfig(format='[%(asctime)s] %(levelname)s - %(message)s',
                    datefmt='%Y-%m-%d %H:%M:%S',
                    level=logging.DEBUG)

def main(args):
    times = args.sleep_times
    if args.cyclic:
        times = itertools.cycle(times)
    for i in times:
        sleep_time = abs(normal(i, args.gaussian))
        logging.debug("Sleeping for %.2f seconds..." % sleep_time)
        time.sleep(sleep_time)
        pyautogui.click()
        logging.debug("Click!")

if __name__ == "__main__":
    args = getArgs()
    logging.info('Program started...')
    logging.info("Sleep times: %s" % args.sleep_times)
    logging.info("Cyclic mode: %s" % ("off", "on")[args.cyclic])
    logging.info("Verbose mode: %s" % ("off", "on")[args.verbose])
    logging.info("Gaussian mode: %s" % ("off", "on")[args.gaussian])
    main(args)
