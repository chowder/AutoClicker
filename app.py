import itertools
import pymouse
import time
from numpy.random import normal
from utils import getArgs, getLogger


def main(args, logger):
    mouse = pymouse.PyMouse()

    times = args.sleep_times
    if args.cyclic:
        times = itertools.cycle(times)

    for i in times:
        sleep_time = normal(i, args.gaussian)
        logger.debug("Sleeping for %.2f seconds..." % sleep_time)
        time.sleep(normal(i, 1))
        logger.debug("Click.")
        x, y = mouse.position()
        mouse.click(x, y)


if __name__ == "__main__":
    args = getArgs()
    logger = getLogger(args)

    logger.info('Program started...')
    logger.info("Sleep times: %s" % args.sleep_times)
    logger.info("Cyclic mode: %s" % ("off", "on")[args.cyclic])
    logger.info("Verbose mode: %s" % ("off", "on")[args.verbose])
    logger.info("Gaussian mode: %s" % ("off", "on")[args.gaussian])

    main(args, logger)
